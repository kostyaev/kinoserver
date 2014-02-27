package ru.cybern.kinoserver.parsers.threads;

import ru.cybern.kinoserver.parsers.kinopoisk.Parser;

import java.io.IOException;

public class KPThread implements Runnable {
    private int from;
    private int to;

    public KPThread (int from, int to) {
        super();
        this.from = from;
        this.to = to;
    }

    @Override
    public void run() {
        Parser kpParser = new Parser();

        try {
            kpParser.parse(from,to);
            kpParser.save();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}