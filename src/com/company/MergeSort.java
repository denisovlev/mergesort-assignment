package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MergeSort {
    private Queue<MyInputStream> sortedStreams;
    private InputStreamFactory inputStreamFactory;
    private OutputStreamFactory outputStreamFactory;
    private int[] sortBuffer;

    public MergeSort(InputStreamFactory inputStreamFactory, OutputStreamFactory outputStreamFactory) {
        this.inputStreamFactory = inputStreamFactory;
        this.outputStreamFactory = outputStreamFactory;
    }

    public MyInputStream sort(String filename, int M, int d) throws IOException {
        sortBuffer = new int[M];
        StreamSplitter s = new StreamSplitter(inputStreamFactory);

        MyInputStream[] streams = s.split(filename, M);
        sortedStreams = sortStreams(streams);

        int n;
        int currentMergeNumber = -1;
        while ((n = sortedStreams.size()) > 1) {
            currentMergeNumber++;
            int streamsCount = (d < n)? d : n;
            MyInputStream[] streamsToMerge = new MyInputStream[streamsCount];
            for (int i = 0; i < streamsCount; i++) {
                streamsToMerge[i] = sortedStreams.poll();
            }
            MyOutputStream out = outputStreamFactory.produce();
            String path = pathnameFor("merge", currentMergeNumber).toString();
            out.create(path);
            MultiWayMerger merger = new MultiWayMerger(streamsToMerge, out);
            merger.merge();
            for (MyInputStream stream : streamsToMerge) {
                new File(stream.getFilename()).delete();
            }
            MyInputStream in = inputStreamFactory.produce();
            in.open(path);
            sortedStreams.add(in);
        }
        return sortedStreams.poll();
    }

    private Queue<MyInputStream> sortStreams(MyInputStream[] streams) throws IOException {
        new File(getResultsDir()).mkdirs();
        Queue<MyInputStream> sortedStreams = new LinkedList<>();
        for (int i = 0; i < streams.length; i++) {
            MyInputStream stream = streams[i];
            int amountRead = readAndSortStream(stream, sortBuffer);
            MyOutputStream out = outputStreamFactory.produce();
            String path = pathnameFor("sorted", i).toString();
            out.create(path);
            writeToStream(out, sortBuffer, amountRead);
            MyInputStream sortedStream = inputStreamFactory.produce();
            sortedStream.open(path);
            sortedStreams.add(sortedStream);
        }
        return sortedStreams;
    }

    private void writeToStream(MyOutputStream out, int[] sortBuffer, int amountRead) throws IOException {
        for (int i = 0; i < amountRead; i++) {
            out.write(sortBuffer[i]);
        }
        out.close();
    }

    private int readAndSortStream(MyInputStream stream, int[] sortBuffer) throws IOException {
        int index = 0;
        while (!stream.end_of_stream()) {
            int res = stream.read_next();
            if (stream.end_of_stream()) break;
            sortBuffer[index] = res;
            index++;
        }
        Arrays.sort(sortBuffer, 0, index);
        return index;
    }

    private Path pathnameFor(String prefix, long number) {
        String resultsDir = getResultsDir();
        String filename = prefix + "_sorted_chunk_" + number + ".data";
        return Paths.get(resultsDir, filename);
    }

    private String getResultsDir() {
        Path currentRelativePath = Paths.get("");
        Path currentDir = currentRelativePath.toAbsolutePath();
        return Paths.get(currentDir.toString(), "results").toString();
    }
}
