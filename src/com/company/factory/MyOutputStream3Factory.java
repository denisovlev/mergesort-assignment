package com.company.factory;

import com.company.streams.MyOutputStream;
import com.company.streams.MyOutputStream3;

public class MyOutputStream3Factory implements OutputStreamFactory {
    private int bufferSize;

    public MyOutputStream3Factory() {
        this(8192 * 2);
    }

    public MyOutputStream3Factory(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public MyOutputStream produce() {

        MyOutputStream3 stream = new MyOutputStream3();
        stream.setBufferSize(bufferSize);
        return stream;
    }
}
