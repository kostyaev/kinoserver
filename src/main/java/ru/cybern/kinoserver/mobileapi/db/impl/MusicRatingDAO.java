package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IMusicRatingDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicRatingEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;

import java.util.List;



public class MusicRatingDAO extends HibernateGenericDAO<MusicRatingEntity, Integer>
        implements IMusicRatingDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<MusicRatingEntity> getMusicRatingsByUser(UserEntity user) {
        return createSQLQuery("SELECT {mr.*} FROM music_rating mr1\n" +
                "WHERE mr1.date_time = ( \n" +
                "SELECT max(date_time) FROM favorites mr2" +
                "WHERE mr1.id = mr2.id and mr1.id = :userId)")
                .addEntity("mr", MusicRatingEntity.class)
                .setParameter("userId", user.getId())
                .list();
    }
}