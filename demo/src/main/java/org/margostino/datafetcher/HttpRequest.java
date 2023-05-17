package org.margostino.datafetcher;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;
import java.util.Map;

@Getter
public class HttpRequest {
    private final Payload payload;
    private final Map<String, String> headers;

    @Builder
    public HttpRequest(Payload payload, @Singular Map<String, String> headers) {
        this.payload = payload;
        this.headers = headers;
    }

    @Getter
    public static class Payload {
        private final String type;
        private final List<String> indicators;
        private final Map<String, Object> arguments;


        @Builder
        public Payload(String type, @Singular List<String> indicators, @Singular Map<String, Object> arguments) {
            this.type = type;
            this.indicators = indicators;
            this.arguments = arguments;
        }
    }
}
