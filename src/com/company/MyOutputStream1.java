package com.company;

import java.io.*;

public class MyOutputStream1 implements MyOutputStream {
    private OutputStream fs;
    private DataOutputStream ds;

    @Override
    public void create(String filename) throws FileNotFoundException {
        File f = new File(filename);
        this.fs = getStream(f);
        this.ds = new DataOutputStream(fs);
    }

    protected OutputStream getStream(File f) throws FileNotFoundException {
        return new FileOutputStream(f);
    }

    @Override
    public void write(int element) throws IOException {
        ds.writeInt(element);
    }

    @Override
    public void close() throws IOException {
        ds.flush();
        fs.close();
    }
}
