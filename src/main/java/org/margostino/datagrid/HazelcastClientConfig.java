package org.margostino.datagrid;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

//@ApplicationScoped
public class HazelcastClientConfig {

//    @Produces
//    protected HazelcastInstance createInstance() {
//        ClientConfig clientConfig = new ClientConfig();
//        String[] members = System.getenv("HAZELCAST_IP").split(",");
//        clientConfig.getNetworkConfig().addAddress(members);
//        return HazelcastClient.newHazelcastClient(clientConfig);
//    }

}