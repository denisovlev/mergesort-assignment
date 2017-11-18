package com.company.benchmark;

import com.company.StreamTuple;
import com.company.manager.StreamManager;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;

/**
 * Created by ivanm on 18/11/2017.
 */
@State(Scope.Thread)
public class StreamBenchmark {

    private static StreamManager manager = StreamManager.getInstance();

    @Param({ "0" })
    private static int stream_type;

    @Param({ "100", "1000", "10000", "10000" })
    private static int N;

    @Benchmark
    public static void test() {
        for (int i = 0; i <= stream_type; i++) {
            for (int k = 1; k <= 30; k++) {
                long startTime = System.nanoTime();
                ArrayList<StreamTuple> streams = new ArrayList<>();
                for (int l = 1; l <= k; l++) {
                    StreamTuple tuple = manager.streamPairs.get(i);
                    tuple.openStreams();
                    streams.add(tuple);
                }
                for (int m = 0; m < k; m++) {
                    StreamTuple pairStream = streams.get(m);
                    //Should be a big number
                    for (int n = 1; n <= N; n++) {
                        pairStream.transfer();
                    }
                }
                //Closing the streams
                streams.forEach(StreamTuple::close);
                long finishTime = System.nanoTime();
                double runTime = (finishTime - startTime) / 1000000000.0;
                //System.out.printf("Experiment with k = %d, N = %d, time = %f\n ", k, N, runTime);
            }
        }
    }
}
