package mobileapi.db.impl;

import mobileapi.db.IMovieDAO;
import mobileapi.db.entities.Film;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class MovieDAO extends HibernateGenericDAO<Film, Integer>
        implements IMovieDAO {
}