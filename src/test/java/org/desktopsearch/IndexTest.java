package org.desktopsearch;

import org.desktopsearch.index.Indexer;
import org.desktopsearch.search.LocalSearcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IndexTest {
    final static String INDEX_PATH = System.getProperty("user.dir").concat(File.separator).concat("index");
    private File index;

    @Before
    public void setUp() throws Exception {
        index = new File("indexDocs");
        index.mkdirs();
        Files.write(Paths.get(new File(index, "doc1").toURI()), "test content".getBytes(), StandardOpenOption.CREATE);
        Indexer.createStarted(index).awaitTermination(2L);
    }

    @After
    public void tearDown() throws Exception {
        LocalFiles.deleteRecursively(index, new File("index"));
    }


    @Test
    public void testIndexesOneDoc() {
        assertNotNull(index.listFiles());
        assertEquals(1, index.listFiles().length);
    }

    @Test
    public void testMaxOneDoc() throws Exception {
        assertEquals(1, LocalSearcher.maxDoc(INDEX_PATH));
    }

    @Test
    public void testHitsOneDoc() throws Exception {
        assertEquals(1,LocalSearcher.hits(INDEX_PATH,"contents:test*"));
    }
}
