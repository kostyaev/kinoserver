package ru.cybern.kinoserver.mobileapi.controllers;

import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmMusicEntity;
import ru.cybern.kinoserver.mobileapi.dto.Update.Method;

import java.util.Date;
import java.util.List;


public interface IFilmBean {

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

    List<FilmHistoryEntity> getFilmHistoryAfterDate(Date date);

    List<FilmHistoryEntity> getFilmHistoryAfterDateByMethod(Date date, Method method);

    List<FilmHistoryEntity> getFilmHistoryAfterDateByMethod(Date date, Method method, int limit, int offset);

    FilmMusicEntity saveFilmMusic(FilmMusicEntity filmMusic);

    void deleteFilmMusic(FilmMusicEntity filmMusic);

    FilmMusicEntity getFilmMusic(int id);

    boolean deleteFilmMusic(int id);

    List<FilmMusicEntity> getFilmMusic();

    List<FilmMusicEntity> getFilmMusicByFilmId(FilmEntity film);

    boolean isExist(String name, int year);

}
