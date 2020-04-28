package net.cserny.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${kafka.topic.name}")
    private String topicName;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String key, String value) {
        ListenableFuture<SendResult<String, String>> futureResult = kafkaTemplate.send(topicName, key, value);
        try {
            futureResult.get();
        } catch (Exception e) {
            LOGGER.warn("Could not send kafka message for topic={}, reason={}", topicName, e.getMessage());
        }
    }
}
