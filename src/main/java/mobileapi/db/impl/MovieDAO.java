package mobileapi.db.impl;

import mobileapi.db.IMovieDAO;
import mobileapi.db.entities.FilmEntity;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class MovieDAO extends HibernateGenericDAO<FilmEntity, Integer>
        implements IMovieDAO {
}