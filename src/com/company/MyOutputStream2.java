package com.company;

import java.io.*;

public class MyOutputStream2 extends MyOutputStream1 {
    @Override
    protected OutputStream getStream(File f) throws FileNotFoundException {
        OutputStream fs = new FileOutputStream(f);
        return new BufferedOutputStream(fs, getBufferSize());
    }

    protected int getBufferSize() {
        return 8192;
    }
}
