package ru.cybern.kinoserver.parsers;

import ru.cybern.kinoserver.parsers.models.Movie;

import java.io.IOException;
import java.util.HashMap;

public interface IParser {

    String getClassName();

    boolean isSaveImages();

    void setSaveImages(boolean saveImages);

    HashMap<String,Movie> parse(int from, int to) throws IOException;




}
