package org.margostino.resources;

import io.smallrye.common.annotation.NonBlocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.margostino.datagrid.DataGrid;
import org.margostino.model.DataMapRequest;
import org.margostino.model.DataMapResponse;

@Path("/map")
@ApplicationScoped
public class DataGridResource {

    @Inject
    DataGrid dataGrid;

    @GET
//    @NonBlocking
    @Produces(MediaType.APPLICATION_JSON)
    public DataMapResponse getData(@QueryParam("key") String key) {
        String value = (String) dataGrid.replicatedMap.get(key);
        return new DataMapResponse(key, value);
    }

    @POST
    @NonBlocking
    @Produces(MediaType.APPLICATION_JSON)
    public DataMapResponse addData(@RequestBody DataMapRequest request) {
        dataGrid.replicatedMap.put(request.key, request.value);
        return new DataMapResponse(request.key, request.value);
    }
}
