package com.benchmark;

import com.company.MergeSort;
import com.company.factory.*;
import com.company.streams.MyInputStream;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
public class MergeSortBenchmark {
    @Param({"12500000", "6250000" })
    private static int M;

    @Param({ "100000000", "250000000"})
    private static int N;

    @Param({ "2", "5", "10", "20"})
    private static int d;

    @Param({"4194304"})
    private static int B;

    @Benchmark
    public Object testMergeSort(Blackhole bh) throws IOException{
        InputStreamFactory inputStreamFactory = new MyInputStream2Factory();
        OutputStreamFactory outputStreamFactory = new MyOutputStream4Factory(B);
        Object o = runTest(inputStreamFactory, outputStreamFactory, M, d, N);
        bh.consume(o);
        return o;
    }

    private Object runTest(InputStreamFactory inputStreamFactory, OutputStreamFactory outputStreamFactory,
                           int M, int d, int N) throws IOException {
        MergeSort mergeSort = new MergeSort(inputStreamFactory, outputStreamFactory, BenchmarkHelper.getFilename(N), M, d);
        MyInputStream stream = mergeSort.sort();
        new File(stream.getFilename()).delete();
        return stream;
    }
}
