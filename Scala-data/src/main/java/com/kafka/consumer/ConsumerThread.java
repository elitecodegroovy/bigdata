package com.kafka.consumer;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/8/23
 */
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class ConsumerThread implements Runnable{
    private KafkaStream stream;
    private int threadNumber;

    public ConsumerThread(KafkaStream a_stream, int a_threadNumber) {
        threadNumber = a_threadNumber;
        stream = a_stream;
    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext())
            System.out.println("--------------" +
                           "Thread " + threadNumber + ", message:" + new String(it.next().message()));
        System.out.println("\n-------------------------------------------------" +
                "Shutting down Thread: " + threadNumber);
    }
}
