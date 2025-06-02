package com.kafka.provider.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProviderConfig {

    @Value("${spring.kafka.bootstrapServers}")
    private String bootstrapServers;

    public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // el servidor donde esta Kafka
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Clase que se va a encargar de serialzar la key del mensaje a una secuencia de bytes
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // Clase que se va a encargar de serialzar el value del mensaje a una secuencia de bytes
        return properties;
    }

    @Bean
    public ProducerFactory<String, String> providerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /*
    *  ¿Qué es KafkaTemplate<String, String>?
        Es una clase proporcionada por Spring for Apache Kafka.

        Se utiliza para enviar mensajes a tópicos de Kafka.

        Los parámetros <String, String> indican que las claves y valores de los mensajes que enviará son cadenas de texto.

        📌 ¿Qué hace ProducerFactory<String, String>?
        Es una fábrica que crea productores de Kafka.

        Proporciona la configuración necesaria para que KafkaTemplate sepa cómo conectar y enviar mensajes a Kafka.

        ✅ ¿Qué hace este método?
        @Bean
        public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
            return new KafkaTemplate<>(producerFactory);
        }
        Este método:

        Recibe una instancia de ProducerFactory<String, String> (probablemente configurada en otro bean).

        Crea una instancia de KafkaTemplate con esa fábrica.

        La retorna como un bean, para que otros componentes de la aplicación puedan inyectarla y usarla para enviar mensajes a Kafka.

        🧠 ¿Por qué es útil?
        Porque permite que puedas usar KafkaTemplate en otras partes de tu aplicación simplemente inyectándola:
        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;
        Y luego:
        kafkaTemplate.send("mi-topico", "Hola Kafka!");

    * */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
