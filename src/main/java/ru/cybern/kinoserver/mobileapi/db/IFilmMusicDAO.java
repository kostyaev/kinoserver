package ru.cybern.kinoserver.mobileapi.db;

import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmMusicEntity;

import java.util.List;


public interface IFilmMusicDAO extends GenericDAO<FilmMusicEntity, Integer> {

    List<FilmMusicEntity> getFilmMusicByFilmId(FilmEntity film);

}
