package org.margostino.resources;

import io.quarkus.logging.Log;
import io.smallrye.common.annotation.NonBlocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
@ApplicationScoped
public class GreetingResource {

//    @Inject
//    DataGrid dataGrid;

    @GET
    @NonBlocking
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Log.info("New Hello Request");
        return "Hello from GraphKeeper!";
    }

}
