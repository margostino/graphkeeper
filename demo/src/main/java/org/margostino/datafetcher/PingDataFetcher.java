package org.margostino.datafetcher;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.margostino.filter.HttpResponseFilter;

@RegisterProvider(HttpResponseFilter.class)
@RegisterRestClient(configKey = "ping")
public interface PingDataFetcher {

    @GET
    @Path("/ping")
    Uni<String> ping();

}
