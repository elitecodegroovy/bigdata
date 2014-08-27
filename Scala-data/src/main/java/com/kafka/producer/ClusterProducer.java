package com.kafka.producer;

import java.util.*;

import com.kafka.utils.PropertiesParser;
import com.kafka.utils.PropertiesSettings;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/8/23
 */
public class ClusterProducer {
    private static final Log log = LogFactory.getLog(ClusterProducer.class);
    public void sendData(){
        long events = Long.parseLong("10");

        Random rnd = new Random();

        Properties props = PropertiesParser.getProperties(PropertiesSettings.PRODUCER_FILE_NAME);
        if(props == null){
            log.error("can't load specified file "+ PropertiesSettings.PRODUCER_FILE_NAME);
            return;
        }
        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<String, String>(config);

        for (long nEvents = 0; nEvents < events; nEvents++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + rnd.nextInt(255);
            String msg = runtime +  ",www.firefox.com, " + ip;
            log.info("set data:"+ msg);
            //send to the topic "cluster_topic"
            KeyedMessage<String, String> data = new KeyedMessage<String,
                                                    String>(PropertiesSettings.TOPIC_NAME, ip, msg);
            producer.send(data);
        }
        producer.close();
    }

    public static void main(String[] args) {
        new ClusterProducer().sendData();
    }
}
