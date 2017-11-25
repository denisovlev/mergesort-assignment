package com.company.streams;

import java.io.*;

public class MyOutputStream2 extends MyOutputStream1 {
    @Override
    protected OutputStream getStream(File f) throws FileNotFoundException {
        OutputStream fs = new FileOutputStream(f);
        return new BufferedOutputStream(fs, getBufferSize());
    }

    public int getBufferSize() {
        return DEFAULT_BUFFER_SIZE;
    }
}
