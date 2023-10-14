package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.example.configs.ConfigConsumer;
import org.example.transformData.FizzBuzz;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

@Slf4j
public class MyConsumer {
    private static final String TOPIC_NAME = "sequence";
    private static final String OUTPUT_FILE = "output.txt";

    public static void main ( String[] args ) {

        try (org.apache.kafka.clients.consumer.Consumer<String, String> consumer = new KafkaConsumer<>
                (ConfigConsumer.configProperties());
             BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {

            TopicPartition topicPartition = new TopicPartition(TOPIC_NAME, 0);

            consumer.assign(Collections.singletonList(topicPartition));

            long lastOffset = consumer.endOffsets(Collections.singletonList(topicPartition)).get(topicPartition);
            long currentOffset = consumer.position(topicPartition);

            while ( currentOffset < lastOffset ) {
                currentOffset = consumer.position(topicPartition);

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    String value = record.value();
                    int number = Integer.parseInt(value);
                    String result = FizzBuzz.fizzBuzz(number);
//                    System.out.println(result);
                    writer.write(result);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}