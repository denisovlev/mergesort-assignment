package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MyOutputStream {
    void create(String filename) throws FileNotFoundException;
    void write(int element) throws IOException;
    void close() throws IOException;
}
