package ru.cybern.kinoserver.mobileapi.db;

import ru.cybern.kinoserver.mobileapi.db.entities.MusicRatingEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.UserEntity;

import java.util.Date;
import java.util.List;


public interface IMusicRatingDAO extends GenericDAO<MusicRatingEntity, Integer> {

    List<MusicRatingEntity> getAfterDateByUser(Date date, UserEntity user);


}
