package org.desktopsearch.utils;

import java.io.File;

public class LocalFiles {

    public static void deleteRecursively(File ... paths) {
        for (final File path : paths) {
            if (path.isDirectory()) {
                for (final File f : path.listFiles()) {
                    deleteRecursively(f);
                }
            }
            path.delete();
        }
    }
}
