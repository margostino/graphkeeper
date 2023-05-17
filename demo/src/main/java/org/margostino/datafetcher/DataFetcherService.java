package org.margostino.datafetcher;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class DataFetcherService {

    public final List<String> indicators;
    private final DataFetcherCaller caller;

    public DataFetcherService(List<String> indicators, DataFetcherCaller caller) {
        this.indicators = indicators;
        this.caller = caller;
    }

    public Uni<HttpResponse> call(HttpRequest.Payload request) {
        Set<String> selectiveIndicators = request.getIndicators().stream()
                .distinct()
                .filter(indicators::contains)
                .collect(toSet());

        HttpRequest.Payload selectiveRequest = HttpRequest.Payload.builder()
                .type(request.getType())
                .arguments(request.getArguments())
                .indicators(selectiveIndicators)
                .build();

        if (selectiveIndicators.isEmpty()) {
            return Uni.createFrom().nullItem();
        }
        
        return caller.query(selectiveRequest);
    }

}
