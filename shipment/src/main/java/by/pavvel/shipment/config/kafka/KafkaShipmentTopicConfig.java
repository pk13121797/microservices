package by.pavvel.shipment.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaShipmentTopicConfig {

    @Bean
    public NewTopic successfulOrdersTopic() {
        return TopicBuilder.name("successful-order").build();
    }
}
