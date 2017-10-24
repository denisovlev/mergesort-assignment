package com.company;

import java.io.*;

public class MyOutputStream1 implements MyOutputStream {
    private File f;
    private OutputStream fs;
    private DataOutputStream ds;

    public void create(String filename) throws FileNotFoundException {
        this.f = new File(filename);
        this.fs = getStream(f);
        this.ds = new DataOutputStream(fs);
    }

    private OutputStream getStream(File f) throws FileNotFoundException {
        return new FileOutputStream(f);
    }

    public void write(int element) throws IOException {
        ds.writeInt(element);
    }

    public void close() throws IOException {
        ds.flush();
        fs.close();
    }
}
