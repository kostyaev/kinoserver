package ru.cybern.kinoserver.parsers;

import ru.cybern.kinoserver.parsers.kinopoisk.Parser;
import ru.cybern.kinoserver.parsers.threads.KPThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PerserCntrl {

    public static void main(String [] args) {
        List<Thread> threads = new ArrayList<Thread>();

        try {
            int pageNumber = Parser.getAmount();
            for (int i = 0; i < pageNumber; i++) {
                threads.add(new Thread(new KPThread(i, i+1)));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // start threads
        for (Thread thread : threads) thread.start();

        // wait threads
        try {
            for (Thread thread : threads)
                thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
