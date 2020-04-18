package net.cserny.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Properties;

public class ConsumerApp {

    private static final String CONSUMER_GROUP = "custom-consumer";
    private static final String TOPIC = "my_topic";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singletonList(TOPIC));
            while (true) {
                ConsumerRecords<String, String> consumerRecords =
                        consumer.poll(Duration.of(30, ChronoUnit.SECONDS));
                if (!consumerRecords.isEmpty()) {
                    for (ConsumerRecord<String, String> record : consumerRecords) {
                        System.out.println(String.format("Message: '%s', from topic %s, partition %d at offset %d",
                                record.value(), record.topic(), record.partition(), record.offset()));
                    }
                } else {
                    System.out.println("No record received yet...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
