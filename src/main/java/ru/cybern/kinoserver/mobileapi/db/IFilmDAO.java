package ru.cybern.kinoserver.mobileapi.db;

import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;

/**
 * Created by virtuozzo on 17.02.14.
 */
public interface IFilmDAO extends GenericDAO<FilmEntity, Integer> {

    boolean isExist(String name, int year);


}
