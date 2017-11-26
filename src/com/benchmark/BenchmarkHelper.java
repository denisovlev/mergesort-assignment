package com.benchmark;

public class BenchmarkHelper {
    static String kthFilename(int length, int i) {
        return "results/stream_" + length + "_" + i + ".data";
    }

    static String getFilename(int N) {
        return "results/input_unsorted_" + N + ".data";
    }
}
