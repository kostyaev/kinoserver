package ru.cybern.kinoserver.mobileapi.db;

import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;

import java.util.List;


public interface IFavoritesDAO extends GenericDAO<FavoritesEntity, Integer> {

    List<FavoritesEntity> getFavoritesByUser(UserEntity user);


}
