package org.margostino.api;

import io.smallrye.common.annotation.NonBlocking;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
@NonBlocking
public class GraphQLResource {

    @Query
    @NonBlocking
    @Description("placeholder")
    public String ping() {
        return "pong";
    }

}
