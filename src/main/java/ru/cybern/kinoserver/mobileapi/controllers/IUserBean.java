package ru.cybern.kinoserver.mobileapi.controllers;

import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicRatingEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;

import java.util.Date;
import java.util.List;


public interface IUserBean {

    UserEntity saveUser(UserEntity user);

    void deleteUser(UserEntity user);

    UserEntity getUser(int id);

    boolean deleteUser(int id);

    List<UserEntity> getUsers();

    List<FavoritesEntity> getFavoritesByUser(Date date, int userId);

    FavoritesEntity saveFavorites(FavoritesEntity favorites);

    List<MusicRatingEntity> getRatingsByUser(Date date, int userId);

    MusicRatingEntity saveMusicRating(MusicRatingEntity musicRating);
}
