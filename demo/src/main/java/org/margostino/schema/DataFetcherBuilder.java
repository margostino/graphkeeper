package org.margostino.schema;

import graphql.schema.DataFetcher;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLFieldDefinition;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.margostino.configuration.DataFetcherConfig;
import org.margostino.configuration.GraphQLConfig;
import org.margostino.datafetcher.DataFetcherCaller;
import org.margostino.datafetcher.DataFetcherService;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class DataFetcherBuilder {

    protected static GraphQLCodeRegistry build(GraphQLConfig graphQLConfig, List<GraphQLFieldDefinition> fieldDefinitions) {
        Map<String, List<DataFetcherConfig>> types = graphQLConfig.types();

        Map<String, DataFetcher<?>> dataFetchers = new HashMap<>();

        for (GraphQLFieldDefinition fieldDefinition : fieldDefinitions) {
            final String fieldDefinitionName = fieldDefinition.getName();
            List<DataFetcherConfig> dataFetcherConfigs = ofNullable(types.get(fieldDefinitionName)).orElse(emptyList());

            List<DataFetcherService> demoDataFetchers = dataFetcherConfigs.stream()
                    .map(DataFetcherBuilder::createHttpService)
                    .collect(toList());

            DemoDataFetcher dataFetcher = new DemoDataFetcher(fieldDefinitionName, demoDataFetchers);
            dataFetchers.put(fieldDefinitionName, dataFetcher);
        }

        return GraphQLCodeRegistry.newCodeRegistry()
                .dataFetchers("Query", dataFetchers)
                .build();
    }

    private static DataFetcherService createHttpService(DataFetcherConfig dataFetcherConfig) {
        DataFetcherCaller caller = RestClientBuilder.newBuilder()
                .baseUri(URI.create(dataFetcherConfig.url()))
                .build(DataFetcherCaller.class);

        return new DataFetcherService(dataFetcherConfig.indicators(), caller);
    }

}
