package com.company;

import com.company.factory.InputStreamFactory;
import com.company.streams.MyInputStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StreamSplitter {
    private InputStreamFactory factory;

    public StreamSplitter(InputStreamFactory factory) {
        this.factory = factory;
    }

    public MyInputStream[] split(String filename, long maxLength) throws IOException {
        long chunksCount = getChunksCount(filename, maxLength);
        ArrayList<MyInputStream> streams = new ArrayList<>();
        for (int i = 0; i < chunksCount; i++) {
            MyInputStream stream = factory.produce();
            stream.open(filename, i * maxLength, maxLength);
            streams.add(stream);
        }
        return streams.toArray(new MyInputStream[0]);
    }

    private long getChunksCount(String filename, long maxLength) {
        long fileLength = new File(filename).length();
        long fileLengthInts = fileLength * Byte.SIZE / Integer.SIZE;
        return (long) Math.ceil((double) fileLengthInts / maxLength);
    }
}
