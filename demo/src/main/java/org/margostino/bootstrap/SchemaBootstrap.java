package org.margostino.bootstrap;

import graphql.schema.GraphQLSchema;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.margostino.configuration.GraphQLConfig;
import org.margostino.schema.SchemaBuilder;


public class SchemaBootstrap {

    @ConfigProperty(name = "graphql.file")
    String schemaSource;

    @Inject
    GraphQLConfig graphQLConfig;

    public GraphQLSchema.Builder build(@Observes GraphQLSchema.Builder builder) {
        return SchemaBuilder.build(schemaSource, graphQLConfig, builder);
    }

}
