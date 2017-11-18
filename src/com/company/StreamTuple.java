package com.company;

import java.io.IOException;

/**
 * Created by ivanm on 18/11/2017.
 */
public class StreamTuple {

    private MyInputStream inputStream;
    private MyOutputStream outputStream;

    public StreamTuple(MyInputStream input, MyOutputStream output) {
        this.inputStream = input;
        this.outputStream = output;
    }

    public void openStreams() {
        //Hardcoded for now
        try {
            inputStream.open("test/test_data.data");
            outputStream.create("test/test_data_out.data");
        } catch (IOException e) {
            System.out.println("IO Exception when opening! Check your input");
        }
    }

    /**
     * Read one element
     */
    public void transfer() {
        try {
            int number = inputStream.read_next();
            outputStream.write(number);
        } catch (IOException e) {
            System.out.println("IO Exception when transfer! Check your input");
        }
    }

    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            System.out.println("IO Exception when closing! Check your input");
        }
    }
}
