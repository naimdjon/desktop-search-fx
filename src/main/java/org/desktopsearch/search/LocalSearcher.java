package org.desktopsearch.search;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

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
}
