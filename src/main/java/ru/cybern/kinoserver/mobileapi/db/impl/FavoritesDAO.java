package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IFavoritesDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;

import java.util.Date;
import java.util.List;

public class FavoritesDAO extends HibernateGenericDAO<FavoritesEntity, Integer>
        implements IFavoritesDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<FavoritesEntity> getFavoritesByUser(UserEntity user) {
        return createSQLQuery("SELECT {fv.*} FROM favorites fv1\n" +
                "WHERE fv1.date_time = ( \n" +
                    "SELECT max(date_time) FROM favorites fv2 \n" +
                    "WHERE fv1.id = fv2.id and fv1.id = :userId)")
                .addEntity("fv", FavoritesEntity.class)
                .setParameter("userId", user.getId())
                .list();
    }
}