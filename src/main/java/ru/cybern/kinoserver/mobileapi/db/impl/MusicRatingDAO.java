package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IMusicRatingDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicRatingEntity;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class MusicRatingDAO extends HibernateGenericDAO<MusicRatingEntity, Integer>
        implements IMusicRatingDAO {
}