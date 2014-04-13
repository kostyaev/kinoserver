package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IMusicRatingDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicRatingEntity;

import java.util.Date;
import java.util.List;



public class MusicRatingDAO extends HibernateGenericDAO<MusicRatingEntity, Integer>
        implements IMusicRatingDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<MusicRatingEntity> getMusicRatingsByUser(int userId, Date date) {
        return createSQLQuery("SELECT {mr1.*} FROM music_rating mr1 \n" +
                "WHERE mr1.date_time > :date AND mr1.date_time = ( \n" +
                "SELECT max(mr2.date_time) FROM music_rating mr2 \n" +
                "WHERE mr2.user_id = :userId) \n")
                .addEntity("mr1", MusicRatingEntity.class)
                .setParameter("userId", userId)
                .setParameter("date", date)
                .list();
    }
}