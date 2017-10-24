package com.company;
import java.io.*;

public class MyInputStream1 implements MyInputStream {
    private InputStream is;
    private DataInputStream ds;
    private boolean eof = false;

    public void open(String filename) throws FileNotFoundException {
        File f = new File(filename);
        this.is = getStream(f);
        this.ds = new DataInputStream(is);
    }

    private InputStream getStream(File f) throws FileNotFoundException {
        return new FileInputStream(f);
    }

    public int read_next() throws IOException {
        try {
            return ds.readInt();
        }
        catch (EOFException e) {
            this.eof = true;
            return -1;
        }
    }

    public boolean end_of_stream() {
        return this.eof;
    }
}
