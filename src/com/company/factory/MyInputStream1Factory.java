package com.company.factory;

import com.company.streams.MyInputStream;
import com.company.streams.MyInputStream1;

public class MyInputStream1Factory implements InputStreamFactory {
    @Override
    public MyInputStream produce() {
        return new MyInputStream1();
    }
}
