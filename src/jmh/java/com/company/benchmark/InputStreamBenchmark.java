package com.company.benchmark;

import com.company.factory.*;
import com.company.streams.MyInputStream;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class InputStreamBenchmark {
    @Param({ "10000000", "100000000" })
    private static int N;

    @Param({ "1", "2", "15", "30" })
//    @Param({ "1", "30" })
    private static int k;

    @Param({"1048576", "4194304"})
//    @Param({ "1048576"})
    private static int B;

    private String kthFilename(int length, int i) {
        return "results/read_stream_" + length + "_" + i + ".data";
    }

    @Benchmark
    public int testMyInputStream1(Blackhole bh) throws IOException {
        InputStreamFactory factory = new MyInputStream1Factory();
        int s =  runTest(factory, N, k, B);
        bh.consume(s);
        return s;
    }

    @Benchmark
    public int testMyInputStream2(Blackhole bh) throws IOException {
        InputStreamFactory factory = new MyInputStream2Factory();
        int s =  runTest(factory, N, k, B);
        bh.consume(s);
        return s;
    }

    @Benchmark
    public int testMyInputStream3(Blackhole bh) throws IOException {
        InputStreamFactory factory = new MyInputStream3Factory(B);
        int s =  runTest(factory, N, k, B);
        bh.consume(s);
        return s;
    }

    @Benchmark
    public int testMyInputStream4(Blackhole bh) throws IOException {
        InputStreamFactory factory = new MyInputStream4Factory(B);
        int s =  runTest(factory, N, k, B);
        bh.consume(s);
        return s;
    }

    private int runTest(InputStreamFactory factory, int n, int k, int b) throws IOException {
        List<MyInputStream> streams = new LinkedList<MyInputStream>();
        for (int i = 0; i < k; i++) {
            MyInputStream stream = factory.produce();
            stream.open(kthFilename(n, i));
        }
        int last = 0;
        for (int i = 0; i < n; i++) {
            for (MyInputStream s: streams) {
                last = s.read_next();
            }
        }
        return last;
    }
}
