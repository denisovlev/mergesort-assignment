package com.benchmark;

import com.company.streams.MyOutputStream;
import com.company.streams.MyOutputStream2;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Generating data");
        generateData(10000000, 30);
        generateData(100000000, 30);
        System.out.println("Running test");

        Options options = new OptionsBuilder()
                .include(com.benchmark.InputStreamBenchmark.class.getSimpleName())
                .warmupIterations(2)
                .measurementIterations(5)
                .forks(1).build();

        new Runner(options).run();
    }

    private static String kthFilename(int length, int i) {
        return "results/read_stream_" + length + "_" + i + ".data";
    }

    private static void generateData(int length, int nFiles) throws IOException {
        String[] filenames = new String[nFiles];
        for (int i = 0; i < nFiles; i++) {
            String filename = kthFilename(length, i);
            filenames[i] = filename;
            MyOutputStream out = new MyOutputStream2();
            out.create(filename);
            for (int j = 0; j < length; j++) {
                out.write(ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE));
            }
            out.close();
        }
    }


}
