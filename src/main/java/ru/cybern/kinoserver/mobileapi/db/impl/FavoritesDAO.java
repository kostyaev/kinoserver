package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IFavoritesDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class FavoritesDAO extends HibernateGenericDAO<FavoritesEntity, Integer>
        implements IFavoritesDAO {
}