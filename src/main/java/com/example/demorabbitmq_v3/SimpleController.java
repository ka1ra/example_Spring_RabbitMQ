package com.example.demorabbitmq_v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/*
* В качестве Издателя (Producer) мы воспользуемся контроллером
* */
@RestController
public class SimpleController {

    Logger logger = LoggerFactory.getLogger(SimpleController.class);

    private final AmqpTemplate template;
    private final RabbitTemplate rabbitTemplate;

    public SimpleController(AmqpTemplate template, RabbitTemplate rabbitTemplate) {
        this.template = template;
        this.rabbitTemplate = rabbitTemplate;
    }


    @PostMapping("emit")
    public ResponseEntity<String> emit(@RequestBody String message) {
        logger.info("Emit to myQueue...");

        template.convertAndSend("myQueue", message);

        return ResponseEntity.ok("Success emit to queue");
    }


    /*
    * проверка, когда имеется больше одного Consumer'a
    * для этого мы отправляем рандомных 10 сообщений на получатели myQueue
    * */
    @PostMapping("emit_to_more_comsumers")
    public ResponseEntity<String> emit_to_more_comsumers(@RequestBody String message) {
        logger.info("Emit to myQueue...");

        for (int index=0; index<10; index++) {
            template.convertAndSend("myQueue", ThreadLocalRandom.current().nextInt());
        }

        return ResponseEntity.ok("Success emit to queue");
    }


    /*
    * проверка, когда имеется больше одного Consumer'a и больше одного Produser'a
    * */
    @PostMapping("emit_to_more_comsumers_from_more_producers")
    public ResponseEntity<String> emit_to_more_comsumers_from_more_producers(@RequestBody String message) {
        logger.info("Emit to myQueue...");

        rabbitTemplate.setExchange("common-exchange");
        rabbitTemplate.convertAndSend(message);

        return ResponseEntity.ok("Success emit to queue");
    }


    /*
    * проверка c DirectExchange
    * */
    @PostMapping("emit_with_direct_exchange")
    public ResponseEntity<String> emit_with_direct_exchange(@RequestBody Map<String, String> map) {
        logger.info("Emit to myQueue...");

        rabbitTemplate.setExchange("direct-exchange");
        rabbitTemplate.convertAndSend(map.get("key"), map.get("message"));

        return ResponseEntity.ok("Success emit to queue");
    }


    /*
    * проверка c TopicExchange
    * */
    @PostMapping("emit_with_topic_exchange")
    public ResponseEntity<String> emit_with_topic_exchange(@RequestBody Map<String, String> map) {
        logger.info("Emit to myQueue...");

        rabbitTemplate.setExchange("topic-exchange");
        rabbitTemplate.convertAndSend(map.get("key"), map.get("message"));

        return ResponseEntity.ok("Success emit to queue");
    }

}
