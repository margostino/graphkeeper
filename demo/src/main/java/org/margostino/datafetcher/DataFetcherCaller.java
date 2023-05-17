package org.margostino.datafetcher;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.margostino.filter.HttpResponseFilter;

@RegisterProvider(HttpResponseFilter.class)
public interface DataFetcherCaller {
    @POST
    Uni<HttpResponse> query(HttpRequest.Payload request);

}
