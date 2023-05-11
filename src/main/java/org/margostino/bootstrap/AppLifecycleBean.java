package org.margostino.bootstrap;

import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import static java.lang.String.format;


@ApplicationScoped
public class AppLifecycleBean {

    void onStart(@Observes StartupEvent ev) {
        String ip = System.getenv("CONTAINER_IP_ADDRESS");
        String profile = ProfileManager.getActiveProfile();
        Log.info(format("GraphKeeper is starting with profile `%s` (%s)", profile, ip));
    }

    void onStop(@Observes ShutdownEvent ev) {
        Log.info("The application is stopping...");
    }

}