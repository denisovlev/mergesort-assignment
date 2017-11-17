package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MyInputStream4 implements MyInputStream {
    private FileChannel channel;
    private boolean eof = false;
    private ByteBuffer buf;
    private long limit = -1;
    private long readCount = 0;
    private String filename;

    @Override
    public void open(String filename, long offset, long limit) throws IOException {
        File f = new File(filename);
        FileInputStream is = new FileInputStream(f);
        skipElements(is, offset);
        this.channel = is.getChannel();
        this.buf = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
        this.limit = limit;
        this.filename = filename;
    }

    private void skipElements(FileInputStream is, long offset) throws IOException {
        long bytesToSkip = offset * Integer.SIZE / Byte.SIZE;
        long skipped = is.skip(bytesToSkip);
        if (skipped != bytesToSkip) throw new IndexOutOfBoundsException();
    }

    public int read_next() throws IOException {
        if (isLimitReached() || ((channel.read(buf)) == -1)) {
            this.eof = true;
            return -1;
        }
        readCount += 1;
        buf.rewind();
        int res = buf.getInt();
        buf.clear();
        return res;
    }

    private boolean isLimitReached() {
        return limit > 0 && limit <= readCount;
    }

    public boolean end_of_stream() {
        return this.eof;
    }

    @Override
    public String getFilename() {
        return filename;
    }
}
