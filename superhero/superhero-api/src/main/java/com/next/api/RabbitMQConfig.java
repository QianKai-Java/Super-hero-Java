package com.next.api;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_TOPICS_PUSH = "exchange_topics_push";
    public static final String QUEUE_NAME_PUSH = "queue_name_push";

    //交换机
    @Bean(EXCHANGE_TOPICS_PUSH)
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_PUSH)
                .durable(true)
                .build();
    }

    //队列
    @Bean(QUEUE_NAME_PUSH)
    public Queue queue() {
        return new Queue(QUEUE_NAME_PUSH);
    }

    //绑定队列到交换机
    @Bean
    public Binding bindingQueueToExchange(@Qualifier(QUEUE_NAME_PUSH) Queue queue,
                                          @Qualifier(EXCHANGE_TOPICS_PUSH) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("push.#.display").noargs();
    }
}
