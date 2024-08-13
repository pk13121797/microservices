package by.pavvel.payment.config.rabbit;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PaymentAMQPConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.order}")
    private String orderQueue;

    @Value("${rabbitmq.queues.reverse-order}")
    private String orderReverseQueue;

    @Value("${rabbitmq.routing-keys.internal-order}")
    private String internalOrderRoutingKey;

    @Value("${rabbitmq.routing-keys.internal-reverse-order}")
    private String internalReverseOrderRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(internalExchange);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueue);
    }

    @Bean
    public Queue orderReverseQueue() {
        return new Queue(orderReverseQueue);
    }

    @Bean
    public Binding internalToOrderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(internalTopicExchange())
                .with(internalOrderRoutingKey);
    }

    @Bean
    public Binding internalToReversedOrderBinding() {
        return BindingBuilder
                .bind(orderReverseQueue())
                .to(internalTopicExchange())
                .with(internalReverseOrderRoutingKey);
    }
}
