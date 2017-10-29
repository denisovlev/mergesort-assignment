package com.company;

public interface InputStreamFactory {
    MyInputStream produce();
}

class MyInputStream1Factory implements InputStreamFactory {
    @Override
    public MyInputStream produce() {
        return new MyInputStream1();
    }
}

class MyInputStream2Factory implements InputStreamFactory {
    @Override
    public MyInputStream produce() {
        return new MyInputStream2();
    }
}

class MyInputStream3Factory implements InputStreamFactory {
    @Override
    public MyInputStream produce() {
        return new MyInputStream3();
    }
}

class MyInputStream4Factory implements InputStreamFactory {
    @Override
    public MyInputStream produce() {
        return new MyInputStream4();
    }
}
