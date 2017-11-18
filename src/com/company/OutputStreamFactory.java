package com.company;

public interface OutputStreamFactory {
    MyOutputStream produce();
}

class MyOutputStream1Factory implements OutputStreamFactory {
    @Override
    public MyOutputStream produce() {
        return new MyOutputStream1();
    }
}

class MyOutputStream2Factory implements OutputStreamFactory {
    @Override
    public MyOutputStream produce() {
        return new MyOutputStream2();
    }
}

class MyOutputStream3Factory implements OutputStreamFactory {
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

class MyOutputStream4Factory implements OutputStreamFactory {
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
