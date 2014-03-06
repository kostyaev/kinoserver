package ru.cybern.kinoserver.mobileapi.db.impl;

import org.hibernate.criterion.Restrictions;
import ru.cybern.kinoserver.mobileapi.db.IFilmHistoryDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;

import java.util.Date;
import java.util.List;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class FilmHistoryDAO extends HibernateGenericDAO<FilmHistoryEntity, Integer>
        implements IFilmHistoryDAO {

    @Override
    public List<FilmHistoryEntity> getAfterDate(Date date) {
        return  createCriteria()
                .add(Restrictions.gt("date_time", date))
                .list();

    }

}