package com.company;

import java.io.*;

public class MyInputStream2 extends MyInputStream1 {
    private static int DEFAULT_BUFFER_SIZE = 8192;

    private InputStream getStream(File f) throws FileNotFoundException {
        InputStream fs = new FileInputStream(f);
        return new BufferedInputStream(fs, DEFAULT_BUFFER_SIZE);
    }
}
