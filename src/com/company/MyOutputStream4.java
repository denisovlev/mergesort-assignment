package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MyOutputStream4 extends MyOutputStream1 {
    private File f;
    private FileOutputStream fs;
    private FileChannel channel;
    private ByteBuffer buf;

    public void create(String filename) throws FileNotFoundException {
        this.f = new File(filename);
        this.fs = new FileOutputStream(f);
        this.channel = fs.getChannel();
        this.buf = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
    }

    public void write(int element) throws IOException {
        buf.clear();
        buf.putInt(element);
        buf.rewind();
        channel.write(buf);
    }

    public void close() throws IOException {
        channel.force(false);
        fs.close();
    }
}
