package ru.cybern.kinoserver.mobileapi;

import ru.cybern.kinoserver.mobileapi.dto.KeepAlive;
import ru.cybern.kinoserver.mobileapi.dto.KeepAliveResponse;
import ru.cybern.kinoserver.mobileapi.dto.UpdateResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.sql.Date;

/**
 * Created by virtuozzo on 14.02.14.
 */

@Path("/mobile")
@Produces("application/json")
@Consumes("application/json")
public class MobileService {
    @POST
    @Path("keepalive")
    public KeepAliveResponse keepAlive(KeepAlive keepAlive) {
        KeepAliveResponse response = new KeepAliveResponse();
        //@TODO
        return response;
    }

    @POST
    @Path("update")
    public UpdateResponse getUpdates(Date lastUpdate) {
        UpdateResponse updates = new UpdateResponse();
        //@TODO

        return updates;

    }




}
