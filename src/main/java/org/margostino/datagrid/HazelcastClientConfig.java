package org.margostino.datagrid;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import static com.hazelcast.core.Hazelcast.newHazelcastInstance;
import static java.lang.String.format;

@ApplicationScoped
public class HazelcastClientConfig {

    @Produces
    protected HazelcastInstance createInstance() {
//        ClientConfig clientConfig = new ClientConfig();
//        String[] members = new String[]{getenv("CONTAINER_IP_ADDRESS")};
//        clientConfig.getNetworkConfig().addAddress(members);

        //Config config = new Config();
        // Network configuration
//        NetworkConfig networkConfig = config.getNetworkConfig();
//        networkConfig.setPortAutoIncrement(true);

        // Interfaces configuration
//        InterfacesConfig interfacesConfig = networkConfig.getInterfaces();
//        interfacesConfig.setEnabled(true).addInterface("127.0.0.1");

        // Join configuration
//        JoinConfig joinConfig = networkConfig.getJoin();
//        joinConfig.getTcpIpConfig().setEnabled(false);

        // Multicast configuration
//        MulticastConfig multicastConfig = joinConfig.getMulticastConfig();
//        multicastConfig.setEnabled(true)
//                .setMulticastGroup("224.2.2.3")
//                .setMulticastPort(54327);


        Config hazelcastConfig = new Config();
        NetworkConfig network = hazelcastConfig.getNetworkConfig();
        network.setPort(5701).setPortCount(20);
        network.setPortAutoIncrement(true);
        JoinConfig join = network.getJoin();
        join.getTcpIpConfig().setEnabled(false);
        join.getMulticastConfig().setEnabled(true);
        //join.getKubernetesConfig().setEnabled(true);
        final String hazelcastManagerUrl = format("http://%s:%s/%s", "localhost", "8080", "hazelcast-manager");
        ManagementCenterConfig manCenterCfg = new ManagementCenterConfig().setConsoleEnabled(true);//.setUrl(hazelcastManagerUrl);
        hazelcastConfig.setManagementCenterConfig(manCenterCfg);
        //return new HazelcastClusterManager(hazelcastConfig);


        return newHazelcastInstance(hazelcastConfig);
    }

}