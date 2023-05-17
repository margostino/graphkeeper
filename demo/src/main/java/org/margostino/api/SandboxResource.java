package org.margostino.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.margostino.datafetcher.HealthCheck;
import org.margostino.datafetcher.PingDataFetcher;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/")
public class SandboxResource {

    @Inject
    @RestClient
    PingDataFetcher pingProvider;

    @Inject
    @RestClient
    HealthCheck healthCheckProvider;

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> ping() {
        return pingProvider.ping();
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World!";
    }

    @GET
    @Path("/healthcheck")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> healthcheck() {
        return healthCheckProvider.healthCheck();
    }

}