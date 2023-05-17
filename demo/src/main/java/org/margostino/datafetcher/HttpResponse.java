package org.margostino.datafetcher;

import graphql.com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class HttpResponse {

    public final String status;
    public final Map<String, Object> indicators;
    public final Map<String, IndicatorError> errors;

    public HttpResponse() {
        this.status = null;
        this.indicators = Collections.emptyMap();
        this.errors = Collections.emptyMap();
    }

    public HttpResponse(final Map<String, Object> indicators, String status) {
        this.status = status;
        this.indicators = indicators;
        this.errors = Collections.emptyMap();
    }

    public HttpResponse(final Map<String, Object> indicators, String status, final Map<String, IndicatorError> errors) {
        this.status = status;
        this.indicators = Collections.unmodifiableMap(indicators);
        this.errors = Collections.unmodifiableMap(errors);
    }

    public static HttpResponse emptyResult() {
        return new HttpResponse();
    }

    public boolean isEmpty() {
        return this.indicators.isEmpty() && this.errors.isEmpty();
    }

    public Set<String> indicatorNames() {
        return Sets.union(this.indicators.keySet(), this.errors.keySet());
    }

    @Override
    public String toString() {
        return String.format("Status: %s - Indicators %d", status, indicators.keySet().size());
    }
}
