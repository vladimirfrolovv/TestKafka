package org.example;

import org.example.consumer.MyConsumer;
import org.example.transform.FizzBuzz;
import org.example.transform.Transformation;

public class Main {
    public static void main ( String[] args ) {
        Transformation transformation = new FizzBuzz();
        MyConsumer myConsumer = new MyConsumer(transformation);
        myConsumer.run();
    }
}
