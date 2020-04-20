package net.cserny.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

public class CustomStringKeyPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        if (partitions.size() <= 1) {
            return 0;
        }

        if (key != null) {
            String keyString = (String) key;
            try {
                int keyInt = Integer.parseInt(keyString.substring(keyString.length() - 1));
                if (keyInt % 2 == 0) {
                    return 2;
                } else {
                    return 1;
                }
            } catch (NumberFormatException ignored) {
            }
        }

        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}