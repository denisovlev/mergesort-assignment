package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MyInputStream {
    public void open(String filename) throws FileNotFoundException;
    public int read_next() throws IOException;
    public boolean end_of_stream();
}
