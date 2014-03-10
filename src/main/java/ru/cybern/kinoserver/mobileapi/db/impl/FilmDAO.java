package ru.cybern.kinoserver.mobileapi.db.impl;

import org.hibernate.criterion.Restrictions;
import ru.cybern.kinoserver.mobileapi.db.IFilmDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class FilmDAO extends HibernateGenericDAO<FilmEntity, Integer>
        implements IFilmDAO {

    @Override
    public boolean isExist(String name, int year) {
        return !createCriteria()
                .add(Restrictions.eq("name", name))
                .add(Restrictions.eq("year", year)).list().isEmpty();

    }
}