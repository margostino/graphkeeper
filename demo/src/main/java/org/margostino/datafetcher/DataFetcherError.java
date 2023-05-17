package org.margostino.datafetcher;

public record DataFetcherError(String type,
                               String dataFetcherName,
                               String field,
                               String errorMessage) {
}
