package org.desktopsearch.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.desktopsearch.utils.Constants;

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
            final TotalHitCountCollector results = new TotalHitCountCollector();
            try (final IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath)))) {
                new IndexSearcher(reader).search(parseQuery(query), results);
                return results.getTotalHits();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int search(final String query, final HitsProcessor processor) {
        try {
            try (final IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(Constants.INDEX_PATH)))) {
                final IndexSearcher indexSearcher = new IndexSearcher(reader);
                final TopDocs results = indexSearcher.search(parseQuery(query), null, 20);
                final ScoreDoc[] scoreDocs = results.scoreDocs;
                for (int i = 0; i < Math.min(10, results.totalHits); i++) {
                    processor.process(indexSearcher.doc(scoreDocs[i].doc));
                }
                return results.totalHits;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public static TopDocs search(final String query) {
        try {
            try (final IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(Constants.INDEX_PATH)))) {
                return new IndexSearcher(reader).search(parseQuery(query), null,20);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Query parseQuery(String query) throws ParseException {
        return new QueryParser(Version.LUCENE_47, "contents", new StandardAnalyzer(Version.LUCENE_47)).parse(query);
    }


    public static interface HitsProcessor{
        public void process(final Document doc);
    }
}
