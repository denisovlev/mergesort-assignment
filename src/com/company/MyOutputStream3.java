package com.company;

public class MyOutputStream3 extends MyOutputStream2 {
    @Override
    protected int getBufferSize() {
        return 8192 * 2;
    }
}
