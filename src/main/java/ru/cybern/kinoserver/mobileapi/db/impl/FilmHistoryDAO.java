package ru.cybern.kinoserver.mobileapi.db.impl;

import org.hibernate.criterion.Restrictions;
import ru.cybern.kinoserver.mobileapi.db.IFilmHistoryDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.dto.Update.Method;

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
                .add(Restrictions.gt("dateTime", date))
                .list();

    }

    @Override
    public List<FilmHistoryEntity> getAfterDateByMethod(Date date, Method method) {
        return  createCriteria()
                .add(Restrictions.gt("dateTime", date))
                .add(Restrictions.eq("method", method.name()))
                .list();

    }

}