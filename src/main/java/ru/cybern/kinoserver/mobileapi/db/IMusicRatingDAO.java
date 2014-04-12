package ru.cybern.kinoserver.mobileapi.db;

import ru.cybern.kinoserver.mobileapi.db.entities.MusicRatingEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;

import java.util.List;


public interface IMusicRatingDAO extends GenericDAO<MusicRatingEntity, Integer> {

    List<MusicRatingEntity> getMusicRatingsByUser(UserEntity user);


}
