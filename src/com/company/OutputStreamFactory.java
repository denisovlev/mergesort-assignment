package com.company;

public interface OutputStreamFactory {
    MyOutputStream produce();
}

class MyOutputStream1Factory implements OutputStreamFactory {
    @Override
    public MyOutputStream produce() {
        return new MyOutputStream1();
    }
}

class MyOutputStream2Factory implements OutputStreamFactory {
    @Override
    public MyOutputStream produce() {
        return new MyOutputStream2();
    }
}

class MyOutputStream3Factory implements OutputStreamFactory {
    @Override
    public MyOutputStream produce() {
        return new MyOutputStream3();
    }
}

class MyOutputStream4Factory implements OutputStreamFactory {
    @Override
    public MyOutputStream produce() {
        return new MyOutputStream4();
    }
}
