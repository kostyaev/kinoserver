package ru.cybern.kinoserver.mobileapi.db;

import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.dto.Update;

import java.util.Date;
import java.util.List;

/**
 * Created by virtuozzo on 17.02.14.
 */
public interface IFilmHistoryDAO extends GenericDAO<FilmHistoryEntity, Integer> {

    List<FilmHistoryEntity> getAfterDate(Date date);

    List<FilmHistoryEntity> getAfterDateByMethod(Date date, Update.Method method);

}
