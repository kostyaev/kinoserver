import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/api")
public class RestServlet {


    @GET
    @Path("/hello/{name}")
    @Produces(MediaType.TEXT_HTML)
    public String setEnter(@PathParam("name") final String name) {

        return "<h1>Welcome, " + name + "</h1>";
    }



}