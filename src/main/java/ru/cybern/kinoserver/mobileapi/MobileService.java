package ru.cybern.kinoserver.mobileapi;

import org.hibernate.Hibernate;
import ru.cybern.kinoserver.mobileapi.actors.ParserManager;
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
import ru.cybern.kinoserver.parsers.Global;

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
import javax.ws.rs.core.Response;
import java.io.File;
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

    @Inject
    ParserManager manager;

    public static final String INIT_DATE = "2014-01-01";


    public Performer getPerformerFrom(PerformerEntity performerEntity) {
        Performer performer = new Performer();
        performer.setId(performerEntity.getId());
        performer.setName(performerEntity.getName());
        return performer;
    }

    public Music getMusicFrom(MusicEntity musicEntity) {
        Music musicDto = new Music();
        musicDto.setId(musicEntity.getId());
        musicDto.setName(musicEntity.getName());
        musicDto.setPerformer(getPerformerFrom(musicEntity.getPerformer()));
        return musicDto;
    }

    public Film getFilmFrom(FilmEntity filmEntity) {
        Film filmDto = new Film();
        filmDto.setId(filmEntity.getId());
        filmDto.setImg(filmEntity.getImg());
        filmDto.setName(filmEntity.getName());
        filmDto.setRating(filmEntity.getRating());
        filmDto.setYear(filmEntity.getYear());
        return filmDto;
    }

    private void addAllFilmMusic(List<FilmMusicEntity> from, List<FilmMusic> to ) {
        for(FilmMusicEntity entry : from) {
            FilmMusic dto = new FilmMusic();
            Hibernate.initialize(entry);
            dto.setId(entry.getId());
            dto.setFilm(getFilmFrom(entry.getFilm()));
            dto.setMusic(getMusicFrom(entry.getMusic()));
            to.add(dto);
        }
    }

    private void addMusic(MusicEntity entity, List<Music> list){
        Music dto = new Music();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPerformer(getPerformerFrom(entity.getPerformer()));
        dto.setRating(0.0);
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
        List<FilmMusic> filmMusic = new LinkedList<>();

        for(FilmHistoryEntity historyEntry : filmHistories) {
            FilmEntity filmEntry = historyEntry.getFilm();
            Hibernate.initialize(filmEntry);
            List<FilmMusicEntity> filmMusicData = filmBean.getFilmMusicByFilmId(filmEntry);
            Hibernate.initialize(filmMusicData);
            addAllFilmMusic(filmMusicData, filmMusic);
            for(FilmMusicEntity filmMusicEntry : filmMusicData) {
                MusicEntity musicEntry = filmMusicEntry.getMusic();
                addMusic(musicEntry, music);
            }
            addFilm(filmEntry, films);
        }

        Update update = new Update();
        update.setFavorites(favorites);
        update.setFilmMusic(filmMusic);
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

    @GET
    @Path("image/{name}")
    @Produces("image/jpeg")
    public Response getImage(@PathParam("name") String filename) {
        File file = new File(Global.HOME_PATH + Global.IMG_PATH + filename);
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=" + filename);
        return response.build();
    }

    @GET
    @Path("start/kinopoisk")
    public void startKinopoisk() {
        manager.startKinopoisk();
    }

    @GET
    @Path("start/whatsong")
    public void startWhatsong() {
        manager.startWhatsong();
    }

}
