package mobileapi.controllers;

import mobileapi.db.entities.FilmEntity;
import mobileapi.db.entities.FilmHistoryEntity;

import java.util.List;


public interface IMovieBean {

    FilmEntity saveFilm(FilmEntity film);

    void deleteFilm(FilmEntity film);

    FilmEntity getFilm(int id);

    boolean deleteFilm(int id);

    List<FilmEntity> getFilms();


    FilmHistoryEntity saveFilmHistory(FilmHistoryEntity filmHistory);

    void deleteFilmHistory(FilmHistoryEntity filmHistory);

    FilmHistoryEntity getFilmHistory(int id);

    boolean deleteFilmHistory(int id);

    List<FilmHistoryEntity> getFilmHistory();





}
