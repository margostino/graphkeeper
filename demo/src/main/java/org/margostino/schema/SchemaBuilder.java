package org.margostino.schema;

import graphql.schema.*;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.margostino.configuration.GraphQLConfig;

import java.util.List;
import java.util.Set;

public class SchemaBuilder {

    public static GraphQLSchema.Builder build(String schemaSource, GraphQLConfig graphQLConfig, GraphQLSchema.Builder builder) {
        TypeDefinitionRegistry schemaDefinition = SchemaLoader.load(schemaSource);
        Set<GraphQLDirective> directives = DirectiveBuilder.build(schemaDefinition);
        List<GraphQLFieldDefinition> indicators = FieldBuilder.build(schemaDefinition, directives);

        GraphQLObjectType queryType = GraphQLObjectType.newObject()
                .name("Query")
                .fields(indicators)
                .build();
        GraphQLCodeRegistry codeRegistry = DataFetcherBuilder.build(graphQLConfig, indicators);

        return builder.query(queryType)
                .clearDirectives()
                .codeRegistry(codeRegistry)
                .additionalDirectives(directives);
    }

}
