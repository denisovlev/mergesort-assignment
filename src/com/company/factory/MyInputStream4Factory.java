package com.company.factory;

import com.company.streams.MyInputStream;
import com.company.streams.MyInputStream4;

public class MyInputStream4Factory implements InputStreamFactory {
    private int bufferSize;

    public MyInputStream4Factory() {
        this(8192 * 2);
    }

    public MyInputStream4Factory(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public MyInputStream produce() {
        MyInputStream4 stream = new MyInputStream4();
        stream.setBufferSize(bufferSize);
        return stream;
    }
}
