package mobileapi.db.impl;

import mobileapi.db.IMusicDAO;
import mobileapi.db.entities.MusicEntity;

/**
 * Created by virtuozzo on 19.02.14.
 */
public class MusicDAO
        extends HibernateGenericDAO<MusicEntity, Integer>
        implements IMusicDAO {
}
