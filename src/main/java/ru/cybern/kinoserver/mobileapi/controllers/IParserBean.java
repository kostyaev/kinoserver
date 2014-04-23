package ru.cybern.kinoserver.mobileapi.controllers;

import ru.cybern.kinoserver.parsers.models.Movie;

import java.util.List;

public interface IParserBean {

    void update(List<Movie> movieList);
}
