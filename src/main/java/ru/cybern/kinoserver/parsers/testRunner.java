package ru.cybern.kinoserver.parsers;

import ru.cybern.kinoserver.parsers.kinopoisk.Parser;

import java.io.IOException;

public class testRunner {
    public static void main(String [] args) throws IOException {
        Parser parser = new Parser();
        int pageCnt = parser.getAmount();
    }
}
