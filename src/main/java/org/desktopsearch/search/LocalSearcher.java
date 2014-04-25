package org.desktopsearch.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TotalHitCountCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;

public class LocalSearcher {

    public static int maxDoc(final String indexPath) {
        try {
            IndexReader reader= DirectoryReader.open(FSDirectory.open(new File(indexPath)));
            try {
                return reader.maxDoc();
            } finally {
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static int hits(final String indexPath,final String query) {
        try {
            final IndexReader reader= DirectoryReader.open(FSDirectory.open(new File(indexPath)));
            final IndexSearcher indexSearcher = new IndexSearcher(reader);
            final TotalHitCountCollector results = new TotalHitCountCollector();
            try {
                indexSearcher.search(new QueryParser(Version.LUCENE_47,"contents",new StandardAnalyzer(Version.LUCENE_47)).parse(query), results);
                return results.getTotalHits();
            } finally {
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
