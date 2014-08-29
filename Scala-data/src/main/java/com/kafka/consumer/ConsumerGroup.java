package com.kafka.consumer;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/8/23
 */

import com.kafka.utils.PropertiesParser;
import com.kafka.utils.PropertiesSettings;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConsumerGroup {
    private static final Log log = LogFactory.getLog(ConsumerGroup.class);
    private final ConsumerConnector consumer;

    private final String topic;
    private ExecutorService executor;

    public ConsumerGroup(String topic) {
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig());
        this.topic = topic;
    }

    public void shutdown() {
        if (consumer != null) consumer.shutdown();
        if (executor != null) executor.shutdown();
    }

    public void run(int numThreads) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        // now launch all the threads
        executor = Executors.newFixedThreadPool(numThreads);

        // now create an object to consume the messages
        //
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            executor.submit(new ConsumerThread(stream, threadNumber));
            threadNumber++;
            log.info("loop index:" + threadNumber);
        }
    }


    private static ConsumerConfig createConsumerConfig() {
        Properties props = PropertiesParser.getProperties(PropertiesSettings.CONSUMER_FILE_NAME);
        return new ConsumerConfig(props);
    }

    public static void main(String[] args) {
//        String zooKeeper = "machine-1:2222,machine-0:2222,machine-2:2222";
//        String groupId = "cluster_group";
//        String topic = "cluster_topic";
        int threads = Integer.parseInt("4");

        ConsumerGroup group = new ConsumerGroup(PropertiesSettings.TOPIC_NAME);
        group.run(threads);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {

        }
        group.shutdown();
    }
}
