package com.benchmark;

import com.company.MergeSort;
import com.company.factory.*;
import com.company.streams.MyInputStream;
import jdk.internal.util.xml.impl.Input;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
public class MergeSortBenchmark {
    @Param({ "10000", "1000000" })
    private static int M;

    @Param({ "1000", "1000000" })
    private static int N;

    @Param({ "3", "10", "20"})
    private static int d;

    @Param({"4194304"})
    private static int B;

    @Benchmark
    public Object testMergeSort(Blackhole bh) throws IOException{
        InputStreamFactory inputStreamFactory = new MyInputStream3Factory(B);
        OutputStreamFactory outputStreamFactory = new MyOutputStream3Factory(B);
        Object o = runTest(inputStreamFactory, outputStreamFactory, M, d, N);
        bh.consume(o);
        return o;
    }

    private Object runTest(InputStreamFactory inputStreamFactory, OutputStreamFactory outputStreamFactory,
                           int M, int d, int N) throws IOException {
        MergeSort mergeSort = new MergeSort(inputStreamFactory, outputStreamFactory, BenchmarkHelper.getFilename(N), M, d);
        return mergeSort.sort();
    }
}
