package net.cserny.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "${kafka.topic.name}", containerFactory = "kafkaListenerFactory", autoStartup = "${kafka.topic.autostart}")
    public void consumerEvent(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        LOGGER.info("Spring message: '{}', from topic {}, partition {} at offset {}",
                record.value(), record.topic(), record.partition(), record.offset());
        acknowledgment.acknowledge();
    }
}
