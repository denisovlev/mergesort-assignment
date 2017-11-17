package com.company;

public class MyOutputStream3 extends MyOutputStream2 {

    private int bufferSize = DEFAULT_BUFFER_SIZE * 2;

    @Override
    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}
