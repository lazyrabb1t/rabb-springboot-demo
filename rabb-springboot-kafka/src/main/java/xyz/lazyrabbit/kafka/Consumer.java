package xyz.lazyrabbit.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@KafkaListener(topics = {"first-topic"}, groupId = "test-consumer-group")
public class Consumer {


    @KafkaHandler
    public void receive(String message) {
        log.info("我是消费者，我接收到的消息是：" + message);
    }
}
