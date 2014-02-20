package parsers.threads;

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
        parsers.kinopoisk.Parser kpParser = new parsers.kinopoisk.Parser();

        try {
            kpParser.parse(from,to);
            kpParser.save();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}