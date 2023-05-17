package org.margostino.datafetcher;

import com.google.common.collect.ImmutableMap;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataFetcherGraphQLError implements GraphQLError {

    private final String message;
    private final List<Object> path;

    public DataFetcherGraphQLError(String message, List<Object> path) {
        this.path = path;
        this.message = message;
    }

    public DataFetcherGraphQLError(DataFetcherError indicatorFetcherError) {
        this.path = new ArrayList<>();

        if (indicatorFetcherError.type() != null) {
            this.path.add(indicatorFetcherError.type());
        }
        if (indicatorFetcherError.field() != null) {
            this.path.add(indicatorFetcherError.field());
        }

        this.message = indicatorFetcherError.errorMessage();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }

    @Override
    public List<Object> getPath() {
        return path;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return ImmutableMap.of("detailedErrorType", this.getClass().getSimpleName());
    }

}
