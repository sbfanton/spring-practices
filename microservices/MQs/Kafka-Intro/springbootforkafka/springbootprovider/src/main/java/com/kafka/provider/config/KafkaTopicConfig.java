package com.kafka.provider.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic generateTopic() {

        Map<String, String> configurations = new HashMap<>();
        configurations.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE); // delete -> borra el mensaje despues de cierto tiempo
        configurations.put(TopicConfig.RETENTION_MS_CONFIG, "86400000"); // Cuanto durara el mensaje dentro del topic en milisegundos - defecto -1
        configurations.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824"); //tamaño maximo de particion o segmento dentro del topic - defecto 1GB
        configurations.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1000012"); //Peso o tamaño maximo de cada mensaje - defecto 1MB

        return TopicBuilder.name("ejemplo1-topic")
                .partitions(2)
                .replicas(2) // dos backups de los mensajes de este topic
                .configs(configurations)
                .build();
    }
}
