package com.company;

public class MyInputStream3 extends MyInputStream2 {
    @Override
    protected int getBufferSize() {
        return 8192 * 2;
    }
}
