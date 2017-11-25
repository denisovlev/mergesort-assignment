package com.company.factory;

import com.company.streams.MyOutputStream;
import com.company.streams.MyOutputStream1;

public class MyOutputStream1Factory implements OutputStreamFactory {
    @Override
    public MyOutputStream produce() {
        return new MyOutputStream1();
    }
}
