package com.company;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyInputStream1Test {

    @Test
    public void myInputStreamTest() throws IOException {
        MyInputStream1 is = new MyInputStream1();
        is.open("test/test_data.data");

        assertEquals(false, is.end_of_stream(), "end o stream should be false");
        assertEquals(1, is.read_next(), "should read 4 bytes");
        assertEquals(256, is.read_next(), "should read 4 bytes");
        is.read_next();
        assertEquals(true, is.end_of_stream(), "end o stream should be true");
    }
}