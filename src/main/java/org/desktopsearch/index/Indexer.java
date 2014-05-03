package org.desktopsearch.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.desktopsearch.Constants;
import org.desktopsearch.utils.Stopwatch;

import java.io.*;
import java.util.concurrent.*;

public class Indexer {
    private final File documentsPath;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Indexer(final File documentPath) {
        this.documentsPath =documentPath;
    }

    public static Indexer createStarted(final File documentsPath) {
        return new Indexer(documentsPath).start();
    }

    public void awaitTermination(final long timeoutSeconds) throws TimeoutException{
        final Stopwatch stopwatch = Stopwatch.start();
        while (!currentFuture.isDone()) {
            if (stopwatch.elapsed(TimeUnit.SECONDS) > timeoutSeconds) {
                throw new TimeoutException("Indexing timed out");
            }
        }
        executor.shutdownNow();
    }

    private Indexer start() {
        index();
        return this;
    }

    private Future currentFuture;

    public void index()throws IllegalStateException {
        if (currentFuture != null && !currentFuture.isDone()) {
            throw new IllegalStateException("Index has already been started!");
        }
        currentFuture = executor.submit(() -> {
            final long start = System.nanoTime();
            try {
                System.out.println("Index path '" + Constants.INDEX_PATH + "'...");
                final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
                final IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
                iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
                try (IndexWriter writer = new IndexWriter(FSDirectory.open(new File(Constants.INDEX_PATH)), iwc)) {
                    indexDocsIfReadable(writer, documentsPath);
                }
            } catch (IOException e) {
                System.out.println(" caught a " + e.getClass() +
                        "\n with message: " + e.getMessage());
            }
            final long end = System.nanoTime();
            System.out.printf("Indexing took %d seconds for the path %s\n", TimeUnit.NANOSECONDS.toSeconds(end - start), documentsPath.getAbsolutePath());
        });
    }

    private void indexDocsIfReadable(IndexWriter writer, File file) throws IOException {
        if (file.canRead()) {
            indexDocsImp(writer, file);
        }
    }

    private  void indexDocsImp(IndexWriter writer, File file) throws IOException {
        if (file.isDirectory()) {
            String[] files = file.list();
            for (int i = 0; files != null && i < files.length; i++) {
                indexDocsIfReadable(writer, new File(file, files[i]));
            }
        } else {
            FileInputStream fis = openStream(file);
            if (fis == null) return;
            try {
                addDocToIndex(writer, file, fis);
            } finally {
                fis.close();
            }
        }
    }

    private  void addDocToIndex(IndexWriter writer, File file, FileInputStream fis) throws IOException {
        Document doc = new Document();
        Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
        doc.add(pathField);
        doc.add(new LongField("modified", file.lastModified(), Field.Store.NO));
        doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));
        if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE) {
            System.out.println("adding " + file);
            writer.addDocument(doc);
        } else {
            System.out.println("updating " + file);
            writer.updateDocument(new Term("path", file.getPath()), doc);
        }
    }

    private static FileInputStream openStream(File file) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException fnfe) {
            return null;
        }
        return fis;
    }
}
