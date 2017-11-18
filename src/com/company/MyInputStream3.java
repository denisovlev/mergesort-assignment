package com.company;

public class MyInputStream3 extends MyInputStream2 {

    protected int bufferSize = DEFAULT_BUFFER_SIZE * 2;

    @Override
    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize =  bufferSize;
    }
}
