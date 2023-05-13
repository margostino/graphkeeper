package org.margostino.resources;

import io.quarkus.logging.Log;
import io.smallrye.common.annotation.NonBlocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.margostino.datagrid.DataGrid;

import java.util.Random;

@Path("/hello")
@ApplicationScoped
public class GreetingResource {

    @Inject
    DataGrid dataGrid;

    @GET
    @NonBlocking
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        Log.info("New Hello Request");
        dataGrid.replicatedMap.put("test", new Random().nextInt(10));
        return "Hello from GraphKeeper!";
    }

}
