package org.margostino.schema;

import com.google.common.base.CaseFormat;
import graphql.execution.DataFetcherResult;
import graphql.schema.*;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.margostino.datafetcher.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.*;

@Getter
@AllArgsConstructor
//public class DemoDataFetcher implements DataFetcher<CompletableFuture<Map<String, Object>>> {
public class DemoDataFetcher implements DataFetcher<CompletableFuture<DataFetcherResult>> {

    private String name;
    private List<DataFetcherService> services;

    @Override
    public CompletableFuture<DataFetcherResult> get(DataFetchingEnvironment environment) {
        Optional<HttpRequest> request = createRequest(environment);

        List<Uni<HttpResponse>> asyncCallResponses = request.map(this::asyncCall)
                .orElse(emptyList());

        return Uni.combine().all()
                .unis(asyncCallResponses)
                .combinedWith(this::combineResponses)
                .onFailure(this::fail)
                .recoverWithItem(this::fallback)
                .subscribeAsCompletionStage();

    }

    private List<Uni<HttpResponse>> asyncCall(HttpRequest request) {
        return services.stream()
                .map(service -> service.call(request.getPayload()))
                .collect(toList());
    }

    private DataFetcherResult combineResponses(List<?> responses) {
        Log.info("Size of responses: " + responses.size());
        Map<String, Object> data = responses.stream()
                .filter(Objects::nonNull)
                .map(HttpResponse.class::cast)
                .map(this::logging)
                .flatMap(response -> response.indicators.entrySet().stream())
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

        return DataFetcherResult.newResult().data(data).build();
    }

    private HttpResponse logging(HttpResponse response) {
        Log.info("Response is: " + response.toString());
        return response;
    }

    private boolean fail(Throwable error) {
        Log.error(format("Error calling HTTP provider: %s", error.getMessage()), error);
        return true;
    }

    private DataFetcherResult fallback(Throwable error) {
        Log.error(format("Error calling HTTP provider: %s", error.getMessage()), error);
        final DataFetcherGraphQLError dataFetcherGraphQLError = new DataFetcherGraphQLError(error.getMessage(), List.of(name));
        return DataFetcherResult.newResult().errors(List.of(dataFetcherGraphQLError)).build();
    }

    private List<RequestedIndicator> getRequestedIndicators(DataFetchingEnvironment environment) {
        final GraphQLSchema schema = environment.getGraphQLSchema();
        final Map<String, List<SelectedField>> selectedIndicators = getSelectedIndicator(environment);

        return selectedIndicators.entrySet()
                .stream()
                .map(indicator -> createRequestedIndicators(schema, indicator))
                .collect(toList());
    }

    private String getDataType(GraphQLSchema schema, String namespace, String indicatorName) {
        final Optional<GraphQLOutputType> type = schema.getQueryType()
                .getFieldDefinition(namespace)
                .getType()
                .getChildren()
                .stream()
                .map(GraphQLFieldDefinition.class::cast)
                .filter(field -> field.getName().equalsIgnoreCase(indicatorName))
                .map(GraphQLFieldDefinition::getType)
                .findAny();

        return type.map(GraphQLScalarType.class::cast)
                .map(scalar -> scalar.getName())
                .orElse("String");
    }

    private Map<String, List<SelectedField>> getSelectedIndicator(DataFetchingEnvironment environment) {
        return environment.getSelectionSet().getFieldsGroupedByResultKey();
    }

    private RequestedIndicator createRequestedIndicators(GraphQLSchema schema, Map.Entry<String, List<SelectedField>> indicator) {
        final String indicatorName = indicator.getKey();
        List<SelectedField> selectedFields = indicator.getValue();
        final String namespace = selectedFields.stream()
                .map(selectedField -> selectedField.getFullyQualifiedName())
                .map(this::parseNamespace)
                .findAny()
                .orElse(indicatorName);
        return new RequestedIndicator(indicatorName, getDataType(schema, namespace, indicatorName));
    }

    private String parseNamespace(String fullyQualifiedName) {
        String[] parts = fullyQualifiedName.split("\\.");

        if (parts.length > 0) {
            String camelCaseNamespace = parts[0].replace("Indicators", "");
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCaseNamespace);
        }

        return fullyQualifiedName;
    }

    private Optional<HttpRequest> createRequest(DataFetchingEnvironment environment) {
        // TODO: validate missing or invalid arguments

        List<RequestedIndicator> requestedIndicators = getRequestedIndicators(environment);
        Map<String, Object> requestedArguments = environment.getArguments();

        final Set<String> indicatorsToForward = requestedIndicators.stream()
                .map(RequestedIndicator::getIndicatorName)
                .collect(toSet());

        return ofNullable(HttpRequest.builder()
                .payload(buildRequestPayload(requestedArguments, indicatorsToForward))
                .header("request-id", "tbd")
                .build());
    }

    private HttpRequest.Payload buildRequestPayload(Map<String, Object> arguments, Set<String> indicators) {
        return HttpRequest.Payload.builder()
                .type(name)
                .arguments(arguments)
                .indicators(indicators)
                .build();
    }

}
