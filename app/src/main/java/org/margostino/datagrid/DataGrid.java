package org.margostino.datagrid;

import com.hazelcast.core.HazelcastInstance;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Map;


@Startup
@Singleton
public class DataGrid {

    public final Map replicatedMap;
    public final HazelcastInstance hzInstance;

    @Inject
    public DataGrid(HazelcastInstance hzInstance) {
        this.hzInstance = hzInstance;
        this.replicatedMap = hzInstance.getReplicatedMap("data");
    }

}