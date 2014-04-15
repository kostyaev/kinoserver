package ru.cybern.kinoserver.parsers;

import ru.cybern.kinoserver.parsers.soundtracknet.Parser;

import java.io.IOException;

public class testRunner {
    public static void main(String [] args) throws IOException {
        Parser parser = new Parser(true);
        int pageCnt = parser.getLastPageNumber();
        parser.parse(1, 2);
    }
}
