package ru.cybern.kinoserver.parsers;

import ru.cybern.kinoserver.parsers.impl.KinopoiskParser;

import java.io.IOException;

public class testRunner {
    public static void main(String [] args) throws IOException {
        System.out.println(KinopoiskParser.getLastPageNumber());
        KinopoiskParser parser = new KinopoiskParser(true);
        parser.parse(1,2);

    }
}
