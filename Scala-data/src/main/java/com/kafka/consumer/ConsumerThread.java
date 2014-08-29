package com.kafka.consumer;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/8/23
 */

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConsumerThread implements Runnable {
    private static final Log log = LogFactory.getLog(ConsumerThread.class);
    private KafkaStream stream;
    private int threadNumber;

    public ConsumerThread(KafkaStream a_stream, int a_threadNumber) {
        threadNumber = a_threadNumber;
        stream = a_stream;
    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext())
            log.info(">>>>>>>>>>>>>>>>>" +
                    "Thread " + threadNumber + ", message:" + new String(it.next().message()));
    }
}
