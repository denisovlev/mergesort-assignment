package com.company.streams;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MyInputStream4 extends MyInputStream3 {
    private FileChannel channel;
    private boolean eof = false;
    private MappedByteBuffer buf;
    private long limit = -1;
    private long readCount = 0;
    private String filename;
    private long offset;

    @Override
    public void open(String filename, long offset, long limit) throws IOException {
        RandomAccessFile f = new RandomAccessFile(filename, "rw");
        this.channel = f.getChannel();
        this.offset = offset;
        this.limit = limit;
        if (limit < 0) {
            this.limit = (channel.size() * Byte.SIZE) / Integer.SIZE;
        }
        if (countInBytes(limit) > (channel.size() - countInBytes(offset))) {
            this.limit = ((channel.size() - countInBytes(offset)) * Byte.SIZE) / Integer.SIZE;
        }
        this.filename = filename;
        remapBuffer(countInBytes(readCount + offset));
    }

    private long countInBytes(long count) {
        return count * Integer.SIZE / Byte.SIZE;
    }

    private void remapBuffer(long position) throws IOException {
        long channelSize = channel.size() - position;
        long size = channelSize < bufferSize ? channelSize : bufferSize;
        buf = channel.map(FileChannel.MapMode.READ_ONLY, position, size);
        buf.load();
    }

    public int read_next() throws IOException {
        if (isLimitReached()) {
            this.eof = true;
            return -1;
        }
        if (buf.remaining() < 1) {
            remapBuffer(countInBytes(readCount + offset));
        }
        readCount += 1;
        return buf.getInt();
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
