package ru.cybern.kinoserver.mobileapi.db.impl;

import org.hibernate.criterion.Restrictions;
import ru.cybern.kinoserver.mobileapi.db.IFavoritesDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;

import java.util.Date;
import java.util.List;

public class FavoritesDAO extends HibernateGenericDAO<FavoritesEntity, Integer>
        implements IFavoritesDAO {

    @Override
    public List<FavoritesEntity> getAfterDateByUser(Date date, UserEntity user) {
        return  createCriteria()
                .add(Restrictions.eq("user", user))
                .add(Restrictions.gt("dateTime", date))
                .list();

    }

}