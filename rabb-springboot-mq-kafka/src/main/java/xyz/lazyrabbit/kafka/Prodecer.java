package xyz.lazyrabbit.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Prodecer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping("send")
    public void send(String message) {
        kafkaTemplate.send("first-topic", message);
    }
}
