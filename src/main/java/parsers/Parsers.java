package parsers;

import parsers.threads.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parsers {
    public static void main(String [] args) {
        List<KPThread> KPThreads = new ArrayList<KPThread>();
        List<STThread> STThreads = new ArrayList<STThread>();

        // need save to db function for movieLibrary
        // thread for each parser

        // Create kinopoisk threads:
        try {
            // init
            int tn = 25; // threads number
            int from = 0;
            int to = 0;
            int ppt = parsers.kinopoisk.Parser.getAmount() / tn;

            for (int i = 0; i < tn; i++) {
                if(i == 0) { // first
                    from = 1;
                    to = ppt;
                } else if(i == 24) { // last
                    from = to;
                    to = ppt - to;
                } else { // otherwise
                    from = to;
                    to += ppt;
                }

                KPThreads.add(new KPThread(from, to));
                break; // test break;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // create stcollect threaads
        // init
        int tn = 91; // final letter 'Z', so it is 'Z' + 1

        for (int i = 0; i < tn; i++) {
            STThreads.add(new STThread(i, i + 1));
            if(i == 9) i = 65; // jump to letter 'A'
            break; // test break;
        }

        // start threads
        for (KPThread thread : KPThreads) thread.start();
        for (STThread thread : STThreads) thread.start();

        // wait threads
        try {
            for (KPThread thread : KPThreads) thread.join();
            for (STThread thread : STThreads) thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}