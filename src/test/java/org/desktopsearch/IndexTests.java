package org.desktopsearch;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.desktopsearch.index.Indexer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IndexTests {
    final static String INDEX_PATH=System.getProperty("user.dir").concat(File.separator).concat("index");
    private IndexWriter writer;
    private File index;
    private Indexer indexer;

    @Before
    public void setUp() throws Exception {
        index = new File("indexDocs");
        index.mkdirs();
        Files.write(Paths.get(new File(index,"doc1").toURI()),"test content".getBytes(), StandardOpenOption.CREATE);
        indexer = new Indexer(index);
    }

    @After
    public void tearDown() throws Exception {
        LocalFiles.deleteRecursively(index, new File("index"));
    }


    @Test public void testIndexesOneDoc() {
        assertNotNull(index.listFiles());
        assertEquals(1,index.listFiles().length);
    }
}
