package hftm.blog;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/blog")
public class Blog {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Willkommen auf meinem Blog!";
    }
}
