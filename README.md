<div align="left">
<h1 align="left">TestKafka</h1>
</div>

## Description:

Проект включает в себя Java приложения, для считывания последовательности чисел из Kafka, и применения 
к ней алгоритма FizzBuzz

## Technologies:
* [Java 17](https://www.java.com/en/)
* [Docker](https://www.docker.com/)
* [Kafka](https://kafka.apache.org/)

## Dependincies:
* [Java SE Development Kit 17+](https://www.oracle.com/java/technologies/downloads/archive/)
* [Docker](https://www.docker.com/)
* [Make](https://en.wikipedia.org/wiki/Make_(software))
* [Maven](https://maven.apache.org/)


### Installation
1. Находясь, в корне проекта, введите в терминале
   ```sh
   make (or "make all")
   ```
2. Затем введите в терминале
   ```sh
   make run
   ```
3. Выходные данные записываются в файл output.txt
4. Чтобы остановить сервис:
   ```sh
   make stop
   ```
