package com.company.streams;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MyOutputStream4 extends MyOutputStream3 {
    private FileChannel channel;
    private MappedByteBuffer buf;
    private RandomAccessFile f;

    @Override
    public void create(String filename) throws IOException {
        f = new RandomAccessFile(filename, "rw");
        f.setLength(Math.max(bufferSize, 1024 * 1024)); // start from min 1 MB
        remapBuffer(0);
    }

    private void remapBuffer(long position) throws IOException {
        if (position + bufferSize > f.length()) {
            f.setLength(f.length() * 2); // grow file to double size
        }
        buf = f.getChannel().map(FileChannel.MapMode.READ_WRITE, position, bufferSize);
    }

    private int bufWritten = 0;

    @Override
    public void write(int element) throws IOException {
        if (buf.remaining() < 1) {
            buf.flip();
            buf.force();
            bufWritten += 1;
            remapBuffer(bufWritten * bufferSize);
        }
        buf.putInt(element);
    }

    @Override
    public void close() throws IOException {
        buf.flip();
        buf.force();
        f.setLength(bufWritten * bufferSize + buf.limit());
    }
}
