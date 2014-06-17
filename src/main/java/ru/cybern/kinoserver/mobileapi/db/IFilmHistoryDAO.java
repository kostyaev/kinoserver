package ru.cybern.kinoserver.mobileapi.db;

import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.dto.Update;

import java.util.Date;
import java.util.List;


public interface IFilmHistoryDAO extends GenericDAO<FilmHistoryEntity, Integer> {

    List<FilmHistoryEntity> getAfterDate(Date date);

    List<FilmHistoryEntity> getAfterDateByMethod(Date date, Update.Method method);

    List<FilmHistoryEntity> getAfterDateByMethod(Date date, Update.Method method, int limit, int offset);

}
