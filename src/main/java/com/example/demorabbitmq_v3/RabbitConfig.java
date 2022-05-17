package com.example.demorabbitmq_v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue myQueue() {
        return new Queue("myQueue");
    }

    /*
    * Указываем Получателя (Consumer)
    * */
    /*@Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        // коннект к Раббит
        container.setConnectionFactory(connectionFactory());

        // это название очереди, на котторую мы подписываемся
        container.setQueueNames("myQueue");

        // при получении сообщения, что необходимо сделать, в нашем случае: пишем сообщение в Логи
        container.setMessageListener(message -> logger.info("Received from myQueue : " + new String(message.getBody())));
        return container;
    }*/


    @Bean
    public Queue myQueue1() {
        return new Queue("myQueue1");
    }
    @Bean
    public Queue myQueue2() {
        return new Queue("myQueue2");
    }
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("common-exchange");
    }
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(myQueue1()).to(fanoutExchange());
    }
    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(myQueue2()).to(fanoutExchange());
    }


    @Bean
    public Queue myQueue_1() {
        return new Queue("myQueue_1");
    }
    @Bean
    public Queue myQueue_2() {
        return new Queue("myQueue_2");
    }
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("direct-exchange");
    }
    @Bean
    public Binding binding_1() {
        return BindingBuilder.bind(myQueue_1()).to(directExchange()).with("error");
    }
    @Bean
    public Binding binding_2() {
        return BindingBuilder.bind(myQueue_2()).to(directExchange()).with("warning");
    }
    @Bean
    public Binding binding_3() {
        return BindingBuilder.bind(myQueue_2()).to(directExchange()).with("info");
    }


    @Bean
    public Queue myQueue_1_topic() {
        return new Queue("myQueue_1_topic");
    }
    @Bean
    public Queue myQueue_2_topic() {
        return new Queue("myQueue_2_topic");
    }
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }
    @Bean
    public Binding binding_1_topic() {
        return BindingBuilder.bind(myQueue_1_topic()).to(topicExchange()).with("one.*");
    }
    @Bean
    public Binding binding_2_topic() {
        return BindingBuilder.bind(myQueue_2_topic()).to(topicExchange()).with("*.second");
    }

}
