package ru.cybern.kinoserver.mobileapi;

import org.hibernate.Hibernate;
import ru.cybern.kinoserver.mobileapi.controllers.IFilmBean;
import ru.cybern.kinoserver.mobileapi.controllers.IMusicBean;
import ru.cybern.kinoserver.mobileapi.controllers.IParserBean;
import ru.cybern.kinoserver.mobileapi.controllers.IUserBean;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmMusicEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.PerformerEntity;
import ru.cybern.kinoserver.mobileapi.dto.Favorites;
import ru.cybern.kinoserver.mobileapi.dto.Film;
import ru.cybern.kinoserver.mobileapi.dto.FilmMusic;
import ru.cybern.kinoserver.mobileapi.dto.Music;
import ru.cybern.kinoserver.mobileapi.dto.Performer;
import ru.cybern.kinoserver.mobileapi.dto.Update;
import ru.cybern.kinoserver.mobileapi.dto.Update.Method;
import ru.cybern.kinoserver.mobileapi.dto.UpdateResponse;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Stateless
@Path("/mobile")
@Produces("application/json;charset=UTF-8")
@Consumes("application/json")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MobileService {

    @Inject
    IFilmBean filmBean;
    @Inject
    IMusicBean musicBean;
    @Inject
    IUserBean userBean;
    @Inject
    IParserBean parserBean;

    public static final String INIT_DATE = "2014-01-01";

    private void addAllFilmMusic(List<FilmMusicEntity> from, List<FilmMusic> to ) {
        for(FilmMusicEntity entry : from) {
            FilmMusic dto = new FilmMusic();
            Hibernate.initialize(entry);
            dto.setId(entry.getId());
            dto.setFilmId(entry.getFilm().getId());
            dto.setMusicId(entry.getMusic().getId());
            to.add(dto);
        }
    }

    private void addMusic(MusicEntity entity, List<Music> list){
        Music dto = new Music();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPerformerId(entity.getPerformer().getId());
        dto.setRating(0); //FIXME
        list.add(dto);
    }

    private void addPerformer(PerformerEntity entity, List<Performer> list){
        Performer dto = new Performer();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        list.add(dto);
    }

    private void addFilm(FilmEntity entity, List<Film> list){
        Film dto = new Film();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImg(entity.getImg());
        dto.setRating(entity.getRating());
        dto.setYear(entity.getYear());
        list.add(dto);
    }

    private Update getUpdates(List<FilmHistoryEntity> filmHistories ) {
        List<Favorites> favorites = new LinkedList<>();
        List<Film> films = new LinkedList<>();
        List<Music> music = new LinkedList<>();
        List<Performer> performers = new LinkedList<>();
        List<FilmMusic> filmMusic = new LinkedList<>();

        for(FilmHistoryEntity historyEntry : filmHistories) {
            FilmEntity filmEntry = historyEntry.getFilm();
            Hibernate.initialize(filmEntry);
            List<FilmMusicEntity> filmMusicData = filmBean.getFilmMusicByFilmId(filmEntry);
            Hibernate.initialize(filmMusicData);
            addAllFilmMusic(filmMusicData, filmMusic);
            for(FilmMusicEntity filmMusicEntry : filmMusicData) {
                MusicEntity musicEntry = filmMusicEntry.getMusic();
                PerformerEntity performerEntry = musicEntry.getPerformer();
                addMusic(musicEntry, music);
                addPerformer(performerEntry, performers);
            }
            addFilm(filmEntry, films);
        }

        Update update = new Update();
        update.setFavorites(favorites);
        update.setFilmMusic(filmMusic);
        update.setFilms(films);
        update.setMusic(music);
        update.setPerformers(performers);
        update.setUpdateDate(new Date());
        update.setMethod(Update.Method.ADD);

        return update;
    }


    @GET
    @Path("update/{date}")
    public UpdateResponse getUpdates(@PathParam("date") String date) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date lastUpdate = null;

        try {
            lastUpdate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<FilmHistoryEntity> addHistories = filmBean.getFilmHistoryAfterDateByMethod(lastUpdate, Method.ADD);
        Update additions = getUpdates(addHistories);
        List<FilmHistoryEntity> deleteHistories = filmBean.getFilmHistoryAfterDateByMethod(lastUpdate, Method.DELETE);
        Update deletions = getUpdates(deleteHistories);
        List<FilmHistoryEntity> updateHistories = filmBean.getFilmHistoryAfterDateByMethod(lastUpdate, Method.UPDATE);
        Update corrections = getUpdates(updateHistories);

        List<Update> updateList = new LinkedList<>();

        updateList.add(additions);
        updateList.add(deletions);
        updateList.add(corrections);

        UpdateResponse updates = new UpdateResponse();
        updates.setUpdates(updateList);

        return updates;
    }

    @GET
    @Path("update")
    public UpdateResponse getAllUpdates() {
        return getUpdates(INIT_DATE);
    }
}
