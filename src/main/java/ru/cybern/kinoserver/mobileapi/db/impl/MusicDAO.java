package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IMusicDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicEntity;

/**
 * Created by virtuozzo on 19.02.14.
 */
public class MusicDAO
        extends HibernateGenericDAO<MusicEntity, Integer>
        implements IMusicDAO {
}
