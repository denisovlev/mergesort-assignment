package com.company;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Thread)
public class Main {
    private static final int SMALL = 1000, MEDIUM = 1000000, LARGE = 1000000000;
    @Param({"1000", "1000000"})
    private static int M;

    @Param({"1000", "1000000"})
    private static int d;

    @Param({"1","2","3","4"})
    private static int streamsType;

    @Param({"1000", "1000000", "100000000",})
    private static int N;

    private static InputStreamFactory inputStreamFactory;
    private static OutputStreamFactory outputStreamFactory;


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



    private static void mergeSort(InputStreamFactory inputFactory, OutputStreamFactory outputFactory,
                                  String filename, int M, int d) throws IOException {
        MergeSort sort = new MergeSort(inputFactory, outputFactory, filename, M, d);
        sort.sort();

    }

    public static void mergeSortHelper(String filename) throws IOException{
        switch (streamsType){
            case 1: mergeSort(  new MyInputStream1Factory(), new MyOutputStream1Factory(),
                    filename, M, d);
            break;
            case 2: mergeSort(  new MyInputStream2Factory(), new MyOutputStream2Factory(),
                    filename, M, d);
            break;
            case 3: mergeSort(  new MyInputStream3Factory(), new MyOutputStream3Factory(),
                    filename, M, d);
            break;
            case 4: mergeSort(  new MyInputStream4Factory(), new MyOutputStream4Factory(),
                    filename, M, d);
            break;

        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public static void benchMarkHelper() throws IOException{
        String filename ;
        switch(N){
            case SMALL:
                filename = "results/"+SMALL+".data";
//                generateRandomData(SMALL, filename);
                mergeSortHelper(filename);
                break;
            case MEDIUM:
                filename = "results/"+MEDIUM+".data";
//                generateRandomData(MEDIUM, filename);
                mergeSortHelper(filename);
                break;
            case LARGE:
                filename = "results/"+LARGE+".data";
//                generateRandomData(LARGE, filename);
                mergeSortHelper(filename);
                break;
            default: break;
        }
    }

    public static void generateData() throws IOException{
        generateRandomData(SMALL, "results/1000.data");
        generateRandomData(MEDIUM, "results/1000000.data");
        generateRandomData(LARGE, "results/100000000.data");
    }


    public static void main(String[] args) throws Exception {
//        generateRandomData(1000);
//        generateData();
        Options options = new OptionsBuilder()
                .warmupIterations(5)
                .measurementIterations(5)
                .include(Main.class.getSimpleName()).forks(1).build();

        new Runner(options).run();
    }

    private static void generateRandomData(int N, String filename) throws IOException {
        MyOutputStream3 out = new MyOutputStream3();
        out.setBufferSize(1000000);
        out.create(filename);
        for (int i = 0; i < N; i++) {
            out.write(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
        out.close();
    }

//    private static void testMergeSort(InputStreamFactory inputFactory ,
//                                      OutputStreamFactory outputFactory, int M, int d) throws IOException {
//        MergeSort sort = new MergeSort(inputFactory, outputFactory);
//        MyInputStream in = sort.sort("results/test_data.data", M, d);
//    }

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
