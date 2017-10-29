package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MyInputStream {
    void open(String filename) throws FileNotFoundException;
    int read_next() throws IOException;
    boolean end_of_stream();
}
