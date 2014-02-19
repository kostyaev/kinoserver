package mobileapi.db.impl;

import mobileapi.db.IMusicDAO;
import mobileapi.db.entities2.Music;

/**
 * Created by virtuozzo on 19.02.14.
 */
public class MusicDAO
        extends HibernateGenericDAO<Music, Integer>
        implements IMusicDAO {
}
