package com.benchmark;

import com.company.streams.MyOutputStream;
import com.company.streams.MyOutputStream4;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws Exception {
//        System.out.println("Generating data");
//        generateData(10000000, 30);
//        generateData(100000000, 30);
        System.out.println("Running test");

        Options outputStreamTestConfig = new OptionsBuilder()
                .include(com.benchmark.OutputStreamBenchmark.class.getSimpleName())
                .warmupIterations(1)
                .measurementIterations(5)
                .resultFormat(ResultFormatType.CSV)
                .forks(1).build();

        new Runner(outputStreamTestConfig).run();

        Options inputStreamTestConfig = new OptionsBuilder()
                .include(com.benchmark.InputStreamBenchmark.class.getSimpleName())
                .warmupIterations(1)
                .measurementIterations(5)
                .resultFormat(ResultFormatType.CSV)
                .forks(1).build();

        new Runner(inputStreamTestConfig).run();
    }

    private static void generateData(int length, int nFiles) throws IOException {
        for (int i = 0; i < nFiles; i++) {
            String filename = BenchmarkHelper.kthFilename(length, i);
            MyOutputStream out = new MyOutputStream4();
            out.create(filename);
            for (int j = 0; j < length; j++) {
                out.write(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE));
            }
            out.close();
        }
    }
}
