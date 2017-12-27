package com.benchmark;

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
@OutputTimeUnit(TimeUnit.SECONDS)
public class InputStreamBenchmark {
    @Param({"25000000", "10000000"})
    private static int N;

    @Param({"1", "5", "10", "15", "20"})
    private static int k;

    @Param({"0", "8096", "16384", "4194304"})
    private static int B;

    @Benchmark
    public int testMyInputStream1(Blackhole bh) throws IOException {
        if (B != 0) return 0; // cannot configure block size for this algorithm
        InputStreamFactory factory = new MyInputStream1Factory();
        int s = runTest(factory, N, k);
        bh.consume(s);
        return s;
    }

    @Benchmark
    public int testMyInputStream2(Blackhole bh) throws IOException {
        if (B != 0) return 0; // cannot configure block size for this algorithm
        InputStreamFactory factory = new MyInputStream2Factory();
        int s = runTest(factory, N, k);
        bh.consume(s);
        return s;
    }

    @Benchmark
    public int testMyInputStream3(Blackhole bh) throws IOException {
        if (B == 0) return 0; // need block size for this algorithm
        InputStreamFactory factory = new MyInputStream3Factory(B);
        int s = runTest(factory, N, k);
        bh.consume(s);
        return s;
    }

    @Benchmark
    public int testMyInputStream4(Blackhole bh) throws IOException {
        if (B == 0) return 0; // need block size for this algorithm
        InputStreamFactory factory = new MyInputStream4Factory(B);
        int s = runTest(factory, N, k);
        bh.consume(s);
        return s;
    }

    private int runTest(InputStreamFactory factory, int n, int k) throws IOException {
        List<MyInputStream> streams = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            MyInputStream stream = factory.produce();
            stream.open(BenchmarkHelper.kthFilename(n, i));
            streams.add(stream);
        }
        int last = 0;
        for (int i = 0; i < n; i++) {
            for (MyInputStream s : streams) {
                last = s.read_next();
            }
        }
        return last;
    }
}
