package org.desktopsearch.utils;

import java.net.URL;

public class Resources {

    public static URL loadResource(final String name) {
        return Resources.class.getClassLoader().getResource(name);
    }
}
