package com.kafka.producer;

import com.kafka.utils.PropertiesParser;
import com.kafka.utils.PropertiesSettings;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;
import java.util.Random;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/8/23
 */
public class ClusterProducer extends Thread {
    private static final Log log = LogFactory.getLog(ClusterProducer.class);

    public void sendData() {
        Random rnd = new Random();
        Properties props = PropertiesParser.getProperties(PropertiesSettings.PRODUCER_FILE_NAME);
        if (props == null) {
            log.error("can't load specified file " + PropertiesSettings.PRODUCER_FILE_NAME);
            return;
        }
        //set the producer configuration properties
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);

        //Send the data
        int count = 1;
        KeyedMessage<String, String> data;
        while (count < 100) {
            String sign = "*";
            String ip = "192.168.2." + rnd.nextInt(255);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < count; i++) {
                sb.append(sign);
            }
            log.info("set data:" + sb);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data = new KeyedMessage<String, String>(PropertiesSettings.TOPIC_NAME, ip, sb.toString());
            producer.send(data);
            count++;
        }
        producer.close();
    }

    public void run() {
        sendData();
    }

    public static void main(String[] args) {
        new ClusterProducer().sendData();
    }
}
