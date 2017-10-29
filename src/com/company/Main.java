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

        testMultiWayMerge();
    }

    private static void testMultiWayMerge() throws IOException {
        System.out.println("Merge test:");
        String inputFilename = "test/test_data.data";
        String outFilename = "test/test_merge_data.data";
        MyInputStream[] streams = {
                new MyInputStream1(),
                new MyInputStream1(),
                new MyInputStream1(),
                new MyInputStream1()
        };
        MyOutputStream out = new MyOutputStream1();
        for (MyInputStream elem : streams) elem.open(inputFilename);
        out.create(outFilename);

        MultiWayMerger merger = new MultiWayMerger(streams, out);
        out = merger.merge();
        out.close();

        MyInputStream is = new MyInputStream1();
        is.open(outFilename);
        while (!is.end_of_stream()) {
            int value = is.read_next();
            if (!is.end_of_stream()) System.out.println(value);
        }
    }

    private static void testInputStream(MyInputStream is) throws IOException {
        System.out.println("Input stream test: " + is.getClass().getSimpleName());
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
