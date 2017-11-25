package com.company.factory;

import com.company.streams.MyOutputStream;

public interface OutputStreamFactory {
    MyOutputStream produce();
}

