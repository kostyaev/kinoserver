package ru.cybern.kinoserver.parsers;

import ru.cybern.kinoserver.parsers.models.Movie;

import java.io.IOException;
import java.util.List;

public interface IParser {

    String getClassName();

    boolean isSaveImages();

    void setSaveImages(boolean saveImages);

    List<Movie> parse(int from, int to) throws IOException;




}
