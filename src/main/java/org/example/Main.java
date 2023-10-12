package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class Main {
    public static void main ( String[] args ) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Main.class);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        System.out.println('1');
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
//            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))
            consumer.subscribe(Collections.singletonList("sequence"));
//            consumer.assign();
//            consumer.seekToBeginning(consumer.);
//            TopicPartition topicPartition = new TopicPartition("sequence",3);
//            consumer.seekToBeginning(Collections.singletonList(topicPartition));

//            Map<String, List<PartitionInfo>> v = consumer.listTopics();
//            System.out.println(v.get("sequence"));
            System.out.println('2');

            consumer.seekToBeginning(consumer.assignment());
            while ( true ) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(3));
//                Set<TopicPartition> topicPartition = records.partitions();
//                System.out.println(topicPartition.size());


                System.out.println(records.count());
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("asfa");
                    System.out.println('5');
                    String value = record.value();
                    int number = Integer.parseInt(value);
                    String result = fizzBuzz(number);
                    System.out.println(result);
//                    writer.write(result);
//                    writer.newLine();

                }
//                consumer.commitAsync();

            }
//        }
//        catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        }

    }

    public static String fizzBuzz ( int number ) {
        if (number % 3 == 0 && number % 5 == 0) {
            return "FizzBuzz";
        } else if (number % 3 == 0) {
            return "Fizz";
        } else if (number % 5 == 0) {
            return "Buzz";
        } else {
            return String.valueOf(number);
        }
    }
}