package com.company;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Thread)
public class Main {
    @Param({"10", "20"})
    private static int M;

    @Param({"3"})
    private static int d;
    private static void test() throws IOException{
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

        testSplitter(new MyInputStream1Factory());
        testSplitter(new MyInputStream2Factory());
        testSplitter(new MyInputStream3Factory());
        testSplitter(new MyInputStream4Factory());
    }

    @Benchmark
    public static void testMergeSort3() throws IOException{
        testMergeSort(new MyInputStream3Factory(32768),
                        new MyOutputStream3Factory(32768), M, d);
    }

    public static void main(String[] args) throws Exception {
        generateRandomData(1000);
        Options options = new OptionsBuilder()
                .include(Main.class.getSimpleName()).forks(1).build();

        new Runner(options).run();
    }

    private static void generateRandomData(int count) throws IOException {
        MyOutputStream1 out = new MyOutputStream1();
        out.create("results/test_data.data");
        for (int i = 0; i < count; i++) {
            out.write(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
        out.close();
    }

    private static void testMergeSort(InputStreamFactory inputFactory,
                                      OutputStreamFactory outputFactory, int M, int d) throws IOException {
        MergeSort sort = new MergeSort(inputFactory, outputFactory);
        MyInputStream in = sort.sort("results/test_data.data", M, d);
    }

    private static void testSplitter(InputStreamFactory factory) throws IOException {
        System.out.println("Splitter test:");
        String inputFilename = "test/test_merge_data.data";
        StreamSplitter s = new StreamSplitter(factory);
        MyInputStream[] streams = s.split(inputFilename, 3);
        for (MyInputStream stream : streams) {
            System.out.println(stream.getClass().getSimpleName());
            System.out.println(stream.read_next());
            System.out.println(stream.read_next());
            System.out.println(stream.read_next());
        }
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
        merger.merge();

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
