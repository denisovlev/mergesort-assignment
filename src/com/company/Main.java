package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        MyOutputStream out = new MyOutputStream1();
        testOutputStream(out);

        MyInputStream is = new MyInputStream1();
        testInputStream(is);

        MyOutputStream out2 = new MyOutputStream2();
        testOutputStream(out2);

        MyInputStream is2 = new MyInputStream2();
        testInputStream(is2);

        MyOutputStream out3 = new MyOutputStream3();
        testOutputStream(out3);

        MyInputStream is3 = new MyInputStream3();
        testInputStream(is3);

        MyOutputStream out4 = new MyOutputStream4();
        testOutputStream(out4);

        MyInputStream is4 = new MyInputStream4();
        testInputStream(is4);
    }

    private static void testInputStream(MyInputStream is) throws IOException {
        is.open("test/test_data.data");
        System.out.println(is.read_next());
        System.out.println(is.read_next());
        is.read_next();
        System.out.println(is.end_of_stream());
    }

    private static void testOutputStream(MyOutputStream out) throws IOException {
        out.create("test/test_data.data");
        out.write(1);
        out.write(256);
        out.close();
    }
}
