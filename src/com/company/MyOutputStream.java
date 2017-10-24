package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MyOutputStream {
    public void create(String filename) throws FileNotFoundException;
    public void write(int element) throws IOException;
    public void close() throws IOException;
}
