package com.benchmark;

import com.company.streams.MyOutputStream;
import com.company.streams.MyOutputStream2;
import com.company.streams.MyOutputStream4;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static final TimeValue TIMEOUT = TimeValue.hours(5);

    public static void main(String[] args) throws Exception {
        System.out.println("Running test");
        initResultsFolder();

//        Options outputStreamTestConfig =
//            new OptionsBuilder().include(com.benchmark.OutputStreamBenchmark.class.getSimpleName())
//                .warmupIterations(1)
//                .measurementIterations(5)
//                .timeout(TIMEOUT)
//                .resultFormat(ResultFormatType.CSV)
//                .forks(1)
//                .build();
//
//        new Runner(outputStreamTestConfig).run();
//        renameResultsFile("output_stream_jmh_result.csv");

//        generateData(25000000, 20);
//        generateData(10000000, 20);
//
//        Options inputStreamTestConfig =
//            new OptionsBuilder().include(com.benchmark.InputStreamBenchmark.class.getSimpleName())
//                .warmupIterations(1)
//                .measurementIterations(5)
//                .timeout(TIMEOUT)
//                .resultFormat(ResultFormatType.CSV)
//                .forks(1)
//                .build();
//
//        new Runner(inputStreamTestConfig).run();
//        renameResultsFile("input_stream_jmh_result.csv");

        initResultsFolder();
        generateMergesortData(100000000);
        generateMergesortData(250000000);

        Options mergeSortTestConfig =
            new OptionsBuilder().include(com.benchmark.MergeSortBenchmark.class.getSimpleName())
                .warmupIterations(1)
                .measurementIterations(5)
                .timeout(TIMEOUT)
                .resultFormat(ResultFormatType.CSV)
                .forks(1)
                .build();

        new Runner(mergeSortTestConfig).run();
        renameResultsFile("merge_stream_jmh_result.csv");
    }

    private static void initResultsFolder() {
        File results = new File("results");
        if (!results.exists()) {
            results.mkdir();
        }
        for(File file: results.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    private static void renameResultsFile(String pathname) {
        new File("jmh-result.csv").renameTo(new File(pathname));
    }

    private static void generateData(int length, int nFiles) throws IOException {
        for (int i = 0; i < nFiles; i++) {
            String filename = BenchmarkHelper.kthFilename(length, i);
            MyOutputStream out = new MyOutputStream2();
            out.create(filename);
            for (int j = 0; j < length; j++) {
                out.write(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE));
            }
            out.close();
        }
    }

    private static void generateMergesortData(int N) throws IOException {
        MyOutputStream out = new MyOutputStream4();
        out.create(BenchmarkHelper.getFilename(N));
        for (int j = 0; j < N; j++) {
            out.write(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
        out.close();
    }
}
