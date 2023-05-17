package org.margostino.datafetcher;

public record IndicatorError(String type, String dataFetcherName, String indicatorName, String errorMessage) {
}