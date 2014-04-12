package ru.cybern.kinoserver.mobileapi.db.impl;

import org.hibernate.criterion.Restrictions;
import ru.cybern.kinoserver.mobileapi.db.IMusicRatingDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicRatingEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;

import java.util.Date;
import java.util.List;



public class MusicRatingDAO extends HibernateGenericDAO<MusicRatingEntity, Integer>
        implements IMusicRatingDAO {

    @Override
    public List<MusicRatingEntity> getAfterDateByUser(Date date, UserEntity user) {
        return  createCriteria()
                .add(Restrictions.eq("user", user))
                .add(Restrictions.gt("dateTime", date))
                .list();

    }
}