package com.company;

public class MyInputStream3 extends MyInputStream2 {

    private int bufferSize = 8192 * 2;

    @Override
    public int getBufferSize() { return bufferSize; }

    public void setBufferSize(int bufferSize) {
        this.bufferSize =  bufferSize;
    }
}
