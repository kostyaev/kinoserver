package ru.cybern.kinoserver.mobileapi.db.impl;

import org.hibernate.criterion.Restrictions;
import ru.cybern.kinoserver.mobileapi.db.IFilmMusicDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmMusicEntity;

import java.util.List;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class FilmMusicDAO extends HibernateGenericDAO<FilmMusicEntity, Integer>
        implements IFilmMusicDAO {

    @Override
    public List<FilmMusicEntity> getFilmMusicByFilmId(FilmEntity film) {
        return  createCriteria()
                .add(Restrictions.eq("film", film))
                .list();
    }
}