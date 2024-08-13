package by.pavvel.payment.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaPaymentTopicConfig {

    @Bean
    public NewTopic paymentsTopic() {
        return TopicBuilder.name("payments").build();
    }

    @Bean
    public NewTopic reversedPaymentsTopic() {
        return TopicBuilder.name("reversed-payments").build();
    }
}
