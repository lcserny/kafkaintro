package net.cserny.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.util.StringUtils;

import java.util.UUID;

public class CorrelationLoggingConsumerInterceptor implements RecordInterceptor<String, String> {

    private static final String CID_KEY = "correlationId";

    @Override
    public ConsumerRecord<String, String> intercept(ConsumerRecord<String, String> record) {
        if (StringUtils.isEmpty(MDC.get(CID_KEY))) {
            MDC.put(CID_KEY, UUID.randomUUID().toString());
        }
        return record;
    }
}
