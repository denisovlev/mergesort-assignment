package com.company.factory;

import com.company.streams.MyInputStream;
import com.company.streams.MyInputStream3;

public class MyInputStream3Factory implements InputStreamFactory {
    private int bufferSize;

    public MyInputStream3Factory() {
        this(8192 * 2);
    }

    public MyInputStream3Factory(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public MyInputStream produce() {
        MyInputStream3 stream = new MyInputStream3();
        stream.setBufferSize(bufferSize);
        return stream;
    }
}
