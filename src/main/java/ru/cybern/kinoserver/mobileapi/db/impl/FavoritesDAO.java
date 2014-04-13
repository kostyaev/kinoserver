package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IFavoritesDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;

import java.util.Date;
import java.util.List;

public class FavoritesDAO extends HibernateGenericDAO<FavoritesEntity, Integer>
        implements IFavoritesDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<FavoritesEntity> getFavoritesByUser(int userId, Date date) {
        return createSQLQuery("SELECT {fv1.*} FROM favorites fv1 \n" +
                "WHERE fv1.date_time > :date AND fv1.date_time = ( \n" +
                    "SELECT max(fv2.date_time) FROM favorites fv2 \n" +
                    "WHERE fv2.user_id = :userId ) \n")
                .addEntity("fv1", FavoritesEntity.class)
                .setParameter("userId", userId)
                .setParameter("date", date)
                .list();
    }
}