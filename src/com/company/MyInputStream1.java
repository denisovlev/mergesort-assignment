package com.company;

import java.io.*;

public class MyInputStream1 implements MyInputStream {
    private DataInputStream ds;
    private boolean eof = false;
    private long limit = -1;
    private long readCount = 0;
    private String filename;
    protected final int DEFAULT_BUFFER_SIZE = 8192; //default java buffered input stream. static var in java has private access

    @Override
    public void open(String filename, long offset, long limit) throws IOException {
        File f = new File(filename);
        InputStream is = getStream(f);

        long bytesToSkip = offset * Integer.SIZE / Byte.SIZE;
        long skipped = is.skip(bytesToSkip);
        if (skipped != bytesToSkip) throw new IndexOutOfBoundsException();
        this.ds = new DataInputStream(is);
        this.limit = limit;
        this.filename = filename;
    }

    protected InputStream getStream(File f) throws FileNotFoundException {
        return new FileInputStream(f);
    }

    @Override
    public int read_next() throws IOException {
        try {
            if (isLimitReached()) throw new EOFException();
            int result = ds.readInt();
            readCount += 1;
            return result;
        } catch (EOFException e) {
            this.eof = true;
            return -1;
        }
    }

    private boolean isLimitReached() {
        return limit > 0 && limit <= readCount;
    }

    @Override
    public boolean end_of_stream() {
        return this.eof;
    }

    @Override
    public String getFilename() {
        return filename;
    }
}
