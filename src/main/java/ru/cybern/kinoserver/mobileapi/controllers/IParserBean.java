package ru.cybern.kinoserver.mobileapi.controllers;

import ru.cybern.kinoserver.parsers.models.Movie;

import java.util.HashMap;

/**
 * Created by virtuozzo on 07.03.14.
 */
public interface IParserBean {
    void update(HashMap<String,Movie> movieLib);
}
