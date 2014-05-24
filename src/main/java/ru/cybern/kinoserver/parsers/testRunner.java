package ru.cybern.kinoserver.parsers;

import ru.cybern.kinoserver.parsers.impl.SoundtracknetParser;

import java.io.IOException;

public class testRunner {
    public static void main(String [] args) throws IOException {
        SoundtracknetParser parser = new SoundtracknetParser(true);
        parser.parse(1,1);

    }
}
