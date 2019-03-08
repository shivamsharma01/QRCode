package com.pp.frenchfries.QRCode.configuration;

import com.pp.frenchfries.QRCode.kafka.producer.Sender;
import com.pp.frenchfries.QRCode.model.QRCode;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SenderConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.schema-registry-url}")
    private String schemaRegistryUrl;

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public Map<String, Object> producerConfigs() throws IOException {
        Map<String, Object> props = new HashMap<>();
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        props.put("ssl.truststore.location", Paths.get(resourceLoader.getResource("classpath:client.truststore.jks").getURI()).toFile().getAbsolutePath());
        props.put("ssl.truststore.password", "secret");
        props.put("ssl.keystore.type", "PKCS12");
        props.put("ssl.keystore.location", Paths.get(resourceLoader.getResource("classpath:client.keystore.p12").getURI()).toFile().getAbsolutePath());
        props.put("ssl.keystore.password", "secret");
        props.put("ssl.key.password", "secret");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() throws IOException {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() throws IOException {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public Sender sender() {
        return new Sender();
    }
}