package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IFilmDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class MovieDAO extends HibernateGenericDAO<FilmEntity, Integer>
        implements IFilmDAO {
}