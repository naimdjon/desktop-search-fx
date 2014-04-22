package org.desktopsearch;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class IndexTests {
    final static String INDEX_PATH=System.getProperty("user.dir").concat(File.separator).concat("index");
    private IndexWriter writer;

    @Test
    public void setUp() throws IOException {
        Directory dir = FSDirectory.open(new File(INDEX_PATH));
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
        writer=new IndexWriter();
    }
}
