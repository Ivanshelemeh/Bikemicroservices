package com.example.bikecustomservise.api.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.AlterConfigOp;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Configuration
public class DynamicTopicConfig {


    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    public void configProperties() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        try (AdminClient adminClient = AdminClient.create(properties)) {
            Map<ConfigResource, Collection<AlterConfigOp>> collectionMap = new HashMap<>();
            ConfigResource resource = new ConfigResource(ConfigResource.Type.TOPIC, "transactions-prosses");

            collectionMap.put(resource, Arrays.asList(
                    new AlterConfigOp(
                            new ConfigEntry("unclean.leader.election.enable", "false"),
                            AlterConfigOp.OpType.SET
                    )
            ));

            adminClient.incrementalAlterConfigs(collectionMap)
                    .all()
                    .get();

            collectionMap.clear();

            resource = new ConfigResource(ConfigResource.Type.TOPIC, "logs-metadata");

            collectionMap.put(resource,
                    Arrays.asList(
                            new AlterConfigOp(
                                    new ConfigEntry("unclean.leader.election.enable", "true"),
                                    AlterConfigOp.OpType.SET
                            )
                    ));
            adminClient.incrementalAlterConfigs(collectionMap)
                    .all()
                    .get();
        }

    }
}
