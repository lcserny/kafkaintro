package net.cserny.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.UnsupportedVersionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerAwareErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.util.List;

public class KafkaConsumerErrorHandler implements ContainerAwareErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerErrorHandler.class);

    @Override
    public void handle(Exception e, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
        if (e instanceof UnsupportedVersionException
                || e instanceof AuthorizationException) {
            LOG.error("Kafka Consumer Fatal Error. Destroying the client...", e);
            container.stop();
        } else {
            LOG.error("Unexpected Kafka consumer error", e);
        }
    }
}
