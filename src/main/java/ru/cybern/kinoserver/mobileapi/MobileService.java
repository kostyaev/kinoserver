package ru.cybern.kinoserver.mobileapi;

import ru.cybern.kinoserver.mobileapi.controllers.IFilmBean;
import ru.cybern.kinoserver.mobileapi.controllers.IMusicBean;
import ru.cybern.kinoserver.mobileapi.controllers.IUserBean;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmEntity;
import ru.cybern.kinoserver.mobileapi.db.entities.FilmHistoryEntity;
import ru.cybern.kinoserver.mobileapi.dto.KeepAlive;
import ru.cybern.kinoserver.mobileapi.dto.KeepAliveResponse;
import ru.cybern.kinoserver.mobileapi.dto.UpdateResponse;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.sql.Date;
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


    @GET
    @Path("update")
    public UpdateResponse getUpdates(Date lastUpdate) {
        List<FilmHistoryEntity> filmHistories = filmBean.getFilmHistoryAfterDate(lastUpdate);
        for(FilmHistoryEntity historyEntry : filmHistories) {
            FilmEntity filmEntry = historyEntry.getFilm();

        }
        UpdateResponse updates = new UpdateResponse();
        //@TODO

        return updates;

    }


    @POST
    @Path("keepalive")
    public KeepAliveResponse keepAlive(KeepAlive keepAlive) {
        KeepAliveResponse response = new KeepAliveResponse();
        //@TODO
        return response;
    }



}
