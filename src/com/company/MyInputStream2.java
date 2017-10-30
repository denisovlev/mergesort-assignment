package com.company;

import java.io.*;

public class MyInputStream2 extends MyInputStream1 {
    @Override
    protected InputStream getStream(File f) throws FileNotFoundException {
        InputStream fs = new FileInputStream(f);
        return new BufferedInputStream(fs, getBufferSize());
    }

    public int getBufferSize() { return 8192; }
}
