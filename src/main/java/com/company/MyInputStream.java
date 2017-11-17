package com.company;

import java.io.IOException;

public interface MyInputStream {

    default void open(String filename) throws IOException {
        open(filename, 0, -1);
    }

    void open(String filename, long offset, long limit) throws IOException;

    int read_next() throws IOException;

    boolean end_of_stream();

    String getFilename();
}
