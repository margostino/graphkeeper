package org.margostino.configuration;

import java.util.List;

public interface DataFetcherConfig {

    String name();

    String url();

    List<String> indicators();

}
