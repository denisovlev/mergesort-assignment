package com.benchmark;

import com.company.factory.*;
import com.company.streams.MyOutputStream;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class OutputStreamBenchmark {
    @Param({"100000", "10000000", "100000000"})
    private static int N;

    @Param({"1", "2", "15", "30"})
    private static int k;

    @Param({"0", "4096", "16384", "4194304"})
    private static int B;

    @Benchmark
    public int testMyOutputStream1(Blackhole bh) throws IOException {
        if (B != 0) return 0; // cannot configure block size for this algorithm
        OutputStreamFactory factory = new MyOutputStream1Factory();
        int s = runTest(factory, N, k);
        bh.consume(s);
        return s;
    }

    @Benchmark
    public int testMyOutputStream2(Blackhole bh) throws IOException {
        if (B != 0) return 0; // cannot configure block size for this algorithm
        OutputStreamFactory factory = new MyOutputStream2Factory();
        int s = runTest(factory, N, k);
        bh.consume(s);
        return s;
    }

    @Benchmark
    public int testMyOutputStream3(Blackhole bh) throws IOException {
        if (B == 0) return 0; // need block size for this algorithm
        OutputStreamFactory factory = new MyOutputStream3Factory(B);
        int s = runTest(factory, N, k);
        bh.consume(s);
        return s;
    }

    @Benchmark
    public int testMyOutputStream4(Blackhole bh) throws IOException {
        if (B == 0) return 0; // need block size for this algorithm
        OutputStreamFactory factory = new MyOutputStream4Factory(B);
        int s = runTest(factory, N, k);
        bh.consume(s);
        return s;
    }

    private int runTest(OutputStreamFactory factory, int n, int k) throws IOException {
        List<MyOutputStream> streams = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            MyOutputStream stream = factory.produce();
            String filename = BenchmarkHelper.kthFilename(n, i);
            (new File(filename)).delete();
            stream.create(filename);
            streams.add(stream);
        }
        int last = 0;
        for (int i = 0; i < n; i++) {
            for (MyOutputStream s : streams) {
                last = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
                s.write(last);
            }
        }
        for (MyOutputStream s : streams) {
            s.close();
        }
        return last;
    }
}
