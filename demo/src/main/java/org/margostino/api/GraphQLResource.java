package org.margostino.api;

import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@Blocking
@GraphQLApi
public class GraphQLResource {

    @Query
    @Description("placeholder")
    @Blocking
    public String ping() {
        return "pong";
    }

}
