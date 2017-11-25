package com.company;

import com.company.factory.InputStreamFactory;
import com.company.factory.OutputStreamFactory;
import com.company.streams.MyInputStream;
import com.company.streams.MyOutputStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of merge sort
 */
public class MergeSort {
    private Queue<MyInputStream> sortedStreams;
    private InputStreamFactory inputStreamFactory;
    private OutputStreamFactory outputStreamFactory;
    private int[] sortBuffer; //help buffer for sort individual stream

    /**
     * Constructor
     * @param inputStreamFactory Factory for input stream
     * @param outputStreamFactory Factory for output stream
     */
    public MergeSort(InputStreamFactory inputStreamFactory, OutputStreamFactory outputStreamFactory) {
        this.inputStreamFactory = inputStreamFactory;
        this.outputStreamFactory = outputStreamFactory;
        sortedStreams = new LinkedList<>();
    }

    /**
     * Sort the input streams
     * @param filename Filename
     * @param M M
     * @param d d
     * @return Sorted stream
     * @throws IOException
     */
    public MyInputStream sort(String filename, int M, int d) throws IOException {
        sortBuffer = new int[M];
        StreamSplitter s = new StreamSplitter(inputStreamFactory);

        MyInputStream[] streams = s.split(filename, M);
        sortedStreams = sortStreams(streams);

        int n;
        int currentMergeNumber = -1;
        //stops when the sorted streams is already 1 file
        while ((n = sortedStreams.size()) > 1) {
            currentMergeNumber++;
            int streamsCount = (d < n) ? d : n;

            //get currently sorted streams to be merged
            MyInputStream[] streamsToMerge = getSortedStreams(streamsCount);

            //get path
            String path = pathnameFor("merge", currentMergeNumber).toString();

            //merge streams
            MultiWayMerger merger = new MultiWayMerger(streamsToMerge, createOutputStream(path));
            merger.merge();

            //streams merged. delete unnecessary
            deleteInputStream(streamsToMerge);

            //add to current sorted streams for next merging process
            addSortedStream(path);
        }
        return sortedStreams.poll(); //return result of sorted streams
    }

    /**
     * return currently sorted streams
     * @param streamsCount number of currently sorted streams
     * @return streams to be merged
     */
    private MyInputStream[] getSortedStreams(int streamsCount){
        MyInputStream[] streamsToMerge = new MyInputStream[streamsCount];
        for (int i = 0; i < streamsCount; i++) {
            streamsToMerge[i] = sortedStreams.poll();
        }
        return streamsToMerge;
    }

    /**
     * Sort array of streams independently
     * @param streams current streams to be sorted
     * @return queue consist of sorted streams
     * @throws IOException IOException
     */
    private Queue<MyInputStream> sortStreams(MyInputStream[] streams) throws IOException {
        new File(getResultsDir()).mkdirs();
        for (int i = 0; i < streams.length; i++) {
            MyInputStream stream = streams[i];
            int amountRead = readAndSortStream(stream);
            String path = pathnameFor("sorted", i).toString();
            writeToStream(createOutputStream(path), amountRead);
            addSortedStream(path);
        }
        return sortedStreams;
    }

    /**
     * Create output stream from the path given
     * @param path Path of output stream
     * @return Output stream
     * @throws IOException IOException
     */
    private MyOutputStream createOutputStream(String path) throws IOException{
        MyOutputStream out = outputStreamFactory.produce();
        out.create(path);
        return out;
    }

    /**
     * Add sorted streams from given path
     * @param path Path of input stream
     * @throws IOException IOException
     */
    private void addSortedStream(String path) throws IOException{
        MyInputStream in = inputStreamFactory.produce();
        in.open(path);
        sortedStreams.add(in);
    }

    /**
     * Delete unnecessary streams after merge process
     * @param myInputStreams Input streams to be deleted
     */
    private void deleteInputStream(MyInputStream[] myInputStreams){
        for (MyInputStream stream : myInputStreams) {
            new File(stream.getFilename()).delete();
        }
    }

    /**
     * Write to output stream
     * @param out Output stream
     * @param amountRead Amount that is going to be read from sort buffer
     * @throws IOException IOException
     */
    private void writeToStream(MyOutputStream out, int amountRead) throws IOException {
        for (int i = 0; i < amountRead; i++) {
            out.write(sortBuffer[i]);
        }
        out.close();
    }

    /**
     * Read and sort curret stream using sort buffer
     * @param stream Stream to be sorted
     * @return last index of sorted element
     * @throws IOException IOException
     */
    private int readAndSortStream(MyInputStream stream) throws IOException {
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

    /**
     * Get path name
     * @param prefix Prefix
     * @param number Current number
     * @return Path name combined with prefix and number
     */
    private Path pathnameFor(String prefix, long number) {
        String resultsDir = getResultsDir();
        String filename = prefix + "_sorted_chunk_" + number + ".data";
        return Paths.get(resultsDir, filename);
    }

    /**
     * Get result directory
     * @return Result directory
     */
    private String getResultsDir() {
        Path currentRelativePath = Paths.get("");
        Path currentDir = currentRelativePath.toAbsolutePath();
        return Paths.get(currentDir.toString(), "results").toString();
    }
}
