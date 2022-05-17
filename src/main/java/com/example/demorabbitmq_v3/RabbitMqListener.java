package com.example.demorabbitmq_v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/*
* Это более простой вариант реализации Consumer'a
* т.е. SimpleMessageListenerContainer messageListenerContainer() из RabbitConfig можно удалить(закоментировать)
* */
@EnableRabbit
@Component
public class RabbitMqListener {

    Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    @RabbitListener(queues = "myQueue")
    public void processMyQueue(String message) {
        logger.info("Received from first listener myQueue: {}", message);
    }

    @RabbitListener(queues = "myQueue")
    public void processMyQueue2(String message) {
        logger.info("Received from second listener myQueue: {}", message);
    }


    @RabbitListener(queues = "myQueue1")
    public void processMyQueue_1(String message) {
        logger.info("Received from myQueue1: {}", message);
    }
    @RabbitListener(queues = "myQueue2")
    public void processMyQueue_2(String message) {
        logger.info("Received from myQueue2: {}", message);
    }


    @RabbitListener(queues = "myQueue_1")
    public void processMyQueue_1_direct(String message) {
        logger.info("Received from myQueue_1: {}", message);
    }
    @RabbitListener(queues = "myQueue_2")
    public void processMyQueue_2_direct(String message) {
        logger.info("Received from myQueue_2: {}", message);
    }


    @RabbitListener(queues = "myQueue_1_topic")
    public void processMyQueue_1_topic(String message) {
        logger.info("Received from myQueue_1_topic: {}", message);
    }
    @RabbitListener(queues = "myQueue_2_topic")
    public void processMyQueue_2_topic(String message) {
        logger.info("Received from myQueue_2_topic: {}", message);
    }

}
