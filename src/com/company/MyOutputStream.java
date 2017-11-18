package com.company;

import java.io.IOException;

public interface MyOutputStream {
    void create(String filename) throws IOException;

    void write(int element) throws IOException;

    void close() throws IOException;
}
