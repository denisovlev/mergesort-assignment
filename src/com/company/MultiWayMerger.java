package com.company;

import com.company.streams.MyInputStream;
import com.company.streams.MyOutputStream;

import java.io.IOException;
import java.util.PriorityQueue;

public class MultiWayMerger {
    private MyInputStream[] streams;
    private MyOutputStream out;

    public MultiWayMerger(MyInputStream[] inputStreams, MyOutputStream out) {
        this.streams = inputStreams;
        this.out = out;
    }

    public MyOutputStream merge() throws IOException {
        PriorityQueue<StreamValueWrapper> queue = new PriorityQueue<>();
        for (MyInputStream elem : streams) {
            StreamValueWrapper wrapper = new StreamValueWrapper(elem);
            if (wrapper.fetchValue()) queue.add(wrapper);
        }

        StreamValueWrapper elem;
        while ((elem = queue.poll()) != null) {
            out.write(elem.getValue());
            if (elem.fetchValue()) queue.add(elem);
        }

        out.close();
        return out;
    }
}

class StreamValueWrapper implements Comparable<StreamValueWrapper> {
    private MyInputStream stream;
    private int value;

    public StreamValueWrapper(MyInputStream stream) {
        this.stream = stream;
    }

    int getValue() {
        return value;
    }

    boolean fetchValue() throws IOException {
        this.value = stream.read_next();
        return !stream.end_of_stream();
    }

    @Override
    public int compareTo(StreamValueWrapper o) {
        return Integer.compare(getValue(), o.getValue());
    }
}
