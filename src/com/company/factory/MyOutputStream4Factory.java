package com.company.factory;

import com.company.streams.MyOutputStream;
import com.company.streams.MyOutputStream4;

public class MyOutputStream4Factory implements OutputStreamFactory {
    private int bufferSize;

    public MyOutputStream4Factory() {
        this(8192 * 2);
    }

    public MyOutputStream4Factory(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public MyOutputStream produce() {

        MyOutputStream4 stream = new MyOutputStream4();
        stream.setBufferSize(bufferSize);
        return stream;
    }
}
