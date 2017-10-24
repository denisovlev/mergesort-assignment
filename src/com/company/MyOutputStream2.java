package com.company;

import java.io.*;

public class MyOutputStream2 extends MyOutputStream1 {
    private static int DEFAULT_BUFFER_SIZE = 8192;

    private OutputStream getStream(File f) throws FileNotFoundException {
        OutputStream fs = new FileOutputStream(f);
        return new BufferedOutputStream(fs, DEFAULT_BUFFER_SIZE);
    }
}
