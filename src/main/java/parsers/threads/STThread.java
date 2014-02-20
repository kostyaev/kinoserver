package parsers.threads;

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
        parsers.stcollect.Parser scParser = new parsers.stcollect.Parser();

        try {
            scParser.parse(from,to);
            scParser.save();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
