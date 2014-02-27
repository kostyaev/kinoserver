package ru.cybern.kinoserver.parsers.threads;

import ru.cybern.kinoserver.parsers.stcollect.Parser;

import java.io.IOException;

public class STThread implements Runnable {
    private int from;
    private int to;

    public STThread (int from, int to) {
        super();
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        Parser scParser = new Parser();

        try {
            scParser.parse(from,to);
            scParser.save();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
