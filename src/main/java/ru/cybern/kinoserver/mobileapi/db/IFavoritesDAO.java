package ru.cybern.kinoserver.mobileapi.db;

import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;

import java.util.Date;
import java.util.List;


public interface IFavoritesDAO extends GenericDAO<FavoritesEntity, Integer> {

    List<FavoritesEntity> getFavoritesByUser(int userId, Date date);


}
