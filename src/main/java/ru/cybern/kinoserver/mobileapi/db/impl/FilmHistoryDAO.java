package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IFilmHistoryDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class FilmHistoryDAO extends HibernateGenericDAO<FilmHistoryEntity, Integer>
        implements IFilmHistoryDAO {
}