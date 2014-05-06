package org.desktopsearch.utils;

import java.io.File;

public class Constants {
    public static final String DEFAULT_INDEX_PATH = System.getProperty("user.dir").concat(File.separator).concat("index");
    public static final String INDEX_PATH = System.getProperty("desktopsearch.index.path", DEFAULT_INDEX_PATH);

    public enum Fields{
        contents
    }

}
