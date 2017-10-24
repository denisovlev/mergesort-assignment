package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MyInputStream4 extends MyInputStream1 {
    private FileInputStream is;
    private FileChannel channel;
    private boolean eof = false;
    private ByteBuffer buf;

    public void open(String filename) throws FileNotFoundException {
        File f = new File(filename);
        this.is = new FileInputStream(f);
        this.channel = is.getChannel();
        this.buf = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
    }

    public int read_next() throws IOException {
        int read = channel.read(buf);
        if (read == -1) {
            this.eof = true;
            return -1;
        }
        buf.rewind();
        int res = buf.getInt();
        buf.clear();
        return res;
    }

    public boolean end_of_stream() {
        return this.eof;
    }
}
