package ru.cybern.kinoserver.mobileapi.db;

import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmMusicEntity;

import java.util.List;

/**
 * Created by virtuozzo on 17.02.14.
 */
public interface IFilmMusicDAO extends GenericDAO<FilmMusicEntity, Integer> {

    List<FilmMusicEntity> getFilmMusicByFilmId(FilmEntity film);

}
