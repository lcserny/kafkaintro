package net.cserny.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.Future;

public class ProducerApp {

    private static final String TOPIC = "in-stream";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
            for (int i = 0; i < 10; i++) {
                Future<RecordMetadata> responseFuture = producer.send(new ProducerRecord<>(
                        TOPIC, String.valueOf(System.currentTimeMillis()),
                        "From application " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
                RecordMetadata metadata = responseFuture.get();
                System.out.println(String.format("Sent to topic %s, partition %d at offset %d",
                        metadata.topic(), metadata.partition(), metadata.offset()));

                Thread.sleep(100L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
