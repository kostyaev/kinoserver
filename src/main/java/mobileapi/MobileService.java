package mobileapi;
import mobileapi.dto.KeepAlive;
import mobileapi.dto.KeepAliveResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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


}
