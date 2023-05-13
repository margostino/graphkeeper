package org.margostino.datagrid;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static com.hazelcast.core.Hazelcast.newHazelcastInstance;
import static java.lang.String.format;

@ApplicationScoped
public class ClusterConfig {

//    @ConfigProperty(name = "hazelcast.network.port.auto-increment")
//    Boolean message;
    @ConfigProperty(name = "database.hostname")
    public String hostname;

    @Produces
    protected HazelcastInstance createInstance() {
        hostname = "";
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


        Config config = new Config();
        //config.setProperty("hazelcast.logging.type", "none");
        //config.setProperty("hazelcast.logging.type", "none");
        //config.setProperty("logger.com.hazelcast.system.logo", "debug");

        NetworkConfig network = config.getNetworkConfig();
        network.setPort(5701).setPortCount(20);
        network.setPortAutoIncrement(true);

        JoinConfig join = network.getJoin();
        join.getTcpIpConfig().setEnabled(false);
        join.getMulticastConfig().setEnabled(true);

        //final String hazelcastManagerUrl = format("http://%s:%s/%s", "localhost", "8080", "hazelcast-manager");
        ManagementCenterConfig manCenterCfg = new ManagementCenterConfig().setConsoleEnabled(true);//.setUrl(hazelcastManagerUrl);
        config.setManagementCenterConfig(manCenterCfg);

        //return newHazelcastInstance(config);
        return newHazelcastInstance();
    }

}