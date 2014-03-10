package ru.cybern.kinoserver.mobileapi;

import ru.cybern.kinoserver.mobileapi.controllers.IFilmBean;
import ru.cybern.kinoserver.mobileapi.controllers.IMusicBean;
import ru.cybern.kinoserver.mobileapi.controllers.IParserBean;
import ru.cybern.kinoserver.mobileapi.controllers.IUserBean;
import ru.cybern.kinoserver.mobileapi.db.entities.FavoritesEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmMusicEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.MusicEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.PerformerEntity;
import ru.cybern.kinoserver.mobileapi.dto.KeepAlive;
import ru.cybern.kinoserver.mobileapi.dto.KeepAliveResponse;
import ru.cybern.kinoserver.mobileapi.dto.UpdateResponse;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by virtuozzo on 14.02.14.
 */

@Path("/mobile")
@Produces("application/json")
@Consumes("application/json")
public class MobileService {

    @Inject
    IFilmBean filmBean;
    @Inject
    IMusicBean musicBean;
    @Inject
    IUserBean userBean;
    @Inject
    IParserBean parserBean;


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
        UpdateResponse updates = new UpdateResponse();

        List<FavoritesEntity> favorites = new LinkedList<FavoritesEntity>();
        List<FilmEntity> films = new LinkedList<FilmEntity>();
        List<MusicEntity> music = new LinkedList<MusicEntity>();
        List<PerformerEntity> performers = new LinkedList<PerformerEntity>();
        List<FilmMusicEntity> filmMusic = new LinkedList<FilmMusicEntity>();

        List<FilmHistoryEntity> filmHistories = filmBean.getFilmHistoryAfterDate(lastUpdate);

        for(FilmHistoryEntity historyEntry : filmHistories) {
            FilmEntity filmEntry = historyEntry.getFilm();
            List<FilmMusicEntity> filmMusicData = filmBean.getFilmMusicByFilmId(filmEntry.getId());
            filmMusic.addAll(filmMusicData);
            for(FilmMusicEntity filmMusicEntry : filmMusicData) {
                MusicEntity musicEntry = filmMusicEntry.getMusic();
                PerformerEntity performerEntry = musicEntry.getPerformer();
                music.add(musicEntry);
                performers.add(performerEntry);
            }
            films.add(filmEntry);
        }
        return updates;
    }


    @POST
    @Path("keepalive")
    public KeepAliveResponse keepAlive(KeepAlive keepAlive) {
        KeepAliveResponse response = new KeepAliveResponse();
        //@TODO
        return response;
    }

    @GET
    @Path("parse")
    public void run() {
        parserBean.update();
    }



}
