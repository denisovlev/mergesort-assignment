package com.company.factory;

import com.company.streams.MyInputStream;
import com.company.streams.MyInputStream2;

public class MyInputStream2Factory implements InputStreamFactory {
    @Override
    public MyInputStream produce() {
        return new MyInputStream2();
    }
}
