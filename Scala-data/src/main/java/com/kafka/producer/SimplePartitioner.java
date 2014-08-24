package com.kafka.producer;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/8/23
 *     public int partition(String key, int a_numPartitions) {

}
 */
import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class SimplePartitioner implements Partitioner {
    public SimplePartitioner (VerifiableProperties props) {
          //TODO verify the properties
    }

    @Override
    public int partition(Object inputKey, int numPartitions) {
        String key = (String) inputKey;
        int partition = 0;
        int offset = key.lastIndexOf('.');
        if (offset > 0) {
            partition = Integer.parseInt( key.substring(offset+1)) % numPartitions;
        }
        return partition;
    }
}