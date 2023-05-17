package org.margostino.datafetcher;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.margostino.filter.HttpResponseFilter;

@RegisterProvider(HttpResponseFilter.class)
@RegisterRestClient(configKey = "healthCheck")
public interface HealthCheck {

    @GET
    @Path("/healthcheck")
    @Timeout(value = 2000)
    @Retry(maxRetries = 1)
    @Fallback(HealthCheckFallback.class)
    Uni<String> healthCheck();

    class HealthCheckFallback implements FallbackHandler<Uni<String>> {

        public Uni<String> handle(ExecutionContext context) {
            Log.info("FALLBACK!");
            return Uni.createFrom().item("fallback");
        }

    }

}
