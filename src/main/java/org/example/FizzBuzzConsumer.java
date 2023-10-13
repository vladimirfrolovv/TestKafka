package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

@Slf4j
public class FizzBuzzConsumer {
    private static final String TOPIC_NAME = "sequence";
    private static final String BOOTSTRAP_SERVERS = "localhost:9093";
    private static final String OUTPUT_FILE = "output.txt";
    public static final String AUTO_OFFSET_RESET_CONFIG = "earliest";

    public static void main ( String[] args ) {
        try (Consumer<String, String> consumer = new KafkaConsumer<>(configProperties());
             BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            TopicPartition topicPartition = new TopicPartition(TOPIC_NAME, 0);
            consumer.assign(Collections.singletonList(topicPartition));
            long lastOffset = consumer.endOffsets(Collections.singletonList(topicPartition)).get(topicPartition);
            System.out.println(lastOffset);
            long currentOffset = consumer.position(topicPartition);
            while ( currentOffset < lastOffset ) {
                currentOffset = consumer.position(topicPartition);
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String value = record.value();
                    int number = Integer.parseInt(value);
                    String result = fizzBuzz(number);
                    System.out.println(result);
                    writer.write(result);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Properties configProperties ( ) {
        final Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return properties;
    }

    public static String fizzBuzz ( int number ) {
        if (number % 3 == 0 && number % 5 == 0) {
            return "fizzbuzz";
        } else if (number % 3 == 0) {
            return "fizz";
        } else if (number % 5 == 0) {
            return "buzz";
        } else {
            return String.valueOf(number);
        }
    }
}