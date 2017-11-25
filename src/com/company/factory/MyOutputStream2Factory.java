package com.company.factory;

import com.company.streams.MyOutputStream;
import com.company.streams.MyOutputStream2;

public class MyOutputStream2Factory implements OutputStreamFactory {
    @Override
    public MyOutputStream produce() {
        return new MyOutputStream2();
    }
}
