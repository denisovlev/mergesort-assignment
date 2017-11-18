package com.company.manager;

import com.company.MyInputStream1;
import com.company.MyInputStream2;
import com.company.MyInputStream3;
import com.company.MyInputStream4;
import com.company.MyOutputStream1;
import com.company.MyOutputStream2;
import com.company.MyOutputStream3;
import com.company.MyOutputStream4;
import com.company.StreamTuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivanm on 18/11/2017.
 */
public class StreamManager {

    private static StreamManager instance = new StreamManager();
    public List<StreamTuple> streamPairs = new ArrayList<>();

    private StreamManager() {
        streamPairs.add(new StreamTuple(new MyInputStream1(), new MyOutputStream1()));
        streamPairs.add(new StreamTuple(new MyInputStream2(), new MyOutputStream2()));
        streamPairs.add(new StreamTuple(new MyInputStream3(), new MyOutputStream3()));
        streamPairs.add(new StreamTuple(new MyInputStream4(), new MyOutputStream4()));
    }

    public static StreamManager getInstance() {
        return instance;
    }
}
