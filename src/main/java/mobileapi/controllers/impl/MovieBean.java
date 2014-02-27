package mobileapi.controllers.impl;

import mobileapi.controllers.*;
import mobileapi.db.entities.FilmEntity;
import mobileapi.db.entities.FilmHistoryEntity;

import java.util.List;

public class MovieBean implements IMovieBean {

    @Override
    public FilmEntity saveFilm(FilmEntity film) {
        return null;
    }

    @Override
    public void deleteFilm(FilmEntity film) {

    }

    @Override
    public FilmEntity getFilm(int id) {
        return null;
    }

    @Override
    public boolean deleteFilm(int id) {
        return false;
    }

    @Override
    public List<FilmEntity> getFilms() {
        return null;
    }

    @Override
    public FilmHistoryEntity saveFilmHistory(FilmHistoryEntity filmHistory) {
        return null;
    }

    @Override
    public void deleteFilmHistory(FilmHistoryEntity filmHistory) {

    }

    @Override
    public FilmHistoryEntity getFilmHistory(int id) {
        return null;
    }

    @Override
    public boolean deleteFilmHistory(int id) {
        return false;
    }

    @Override
    public List<FilmHistoryEntity> getFilmHistory() {
        return null;
    }
}
