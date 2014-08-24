package com.kafka.producer;

import java.util.*;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/8/23
 */
public class ClusterProducer {

    public void sendData(){
        long events = Long.parseLong("12");

        Random rnd = new Random();

        Properties props = new Properties();
        props.put("metadata.broker.list", "hadoop-master:9092,machine-0:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "com.kafka.producer.SimplePartitioner");
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<String, String>(config);

        for (long nEvents = 0; nEvents < events; nEvents++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + rnd.nextInt(255);
            String msg = runtime +  ",www.firefox.com, " + ip;
            //send to the topic "cluster_topic"
            KeyedMessage<String, String> data = new KeyedMessage<String, String>("cluster_topic", ip, msg);
            producer.send(data);
        }
        producer.close();
    }

    public static void main(String[] args) {
           new ClusterProducer().sendData();
    }
}
