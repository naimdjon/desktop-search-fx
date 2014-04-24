package org.desktopsearch.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.Date;

public class Indexer {
    public static final String  INDEX_PATH=System.getProperty("destopsearch.index.path",System.getProperty("user.dir").concat(File.separator).concat("index"));

    public Indexer(File path) {
        index(path);
    }

    public void index(File path) {
        final Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + INDEX_PATH + "'...");
            final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
            final IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            final IndexWriter writer = new IndexWriter(FSDirectory.open(new File(INDEX_PATH)), iwc);
            try {
                indexDocsIfReadable(writer, path);
            } finally {
                writer.close();
            }
            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");
        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }

    static void indexDocsIfReadable(IndexWriter writer, File file) throws IOException {
        if (file.canRead()) {
            indexDocsImp(writer, file);
        }
    }

    private static void indexDocsImp(IndexWriter writer, File file) throws IOException {
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

    private static void addDocToIndex(IndexWriter writer, File file, FileInputStream fis) throws IOException {
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
