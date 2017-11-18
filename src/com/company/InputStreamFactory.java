package com.company;

public interface InputStreamFactory {
    MyInputStream produce();
}

class MyInputStream1Factory implements InputStreamFactory {
    @Override
    public MyInputStream produce() {
        return new MyInputStream1();
    }
}

class MyInputStream2Factory implements InputStreamFactory {
    @Override
    public MyInputStream produce() {
        return new MyInputStream2();
    }
}

class MyInputStream3Factory implements InputStreamFactory {
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

class MyInputStream4Factory implements InputStreamFactory {
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
