package org.margostino.configuration;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

import java.util.List;
import java.util.Map;

@StaticInitSafe
@ConfigMapping(prefix = "graphql")
public interface GraphQLConfig {

    String file();
    Map<String, List<DataFetcherConfig>> types();

}
