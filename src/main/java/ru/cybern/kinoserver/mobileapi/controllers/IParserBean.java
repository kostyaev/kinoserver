package ru.cybern.kinoserver.mobileapi.controllers;

import ru.cybern.kinoserver.parsers.models.Movie;

import java.util.HashMap;

public interface IParserBean {

    void update(HashMap<String,Movie> movieLib);
}
