.PHONY: start stop run clean

all: start

TOPIC_NAME=sequence
KAFKA_BROKER=localhost:9092

start:
	@docker-compose up -d
run:
	@docker-compose exec kafka kafka-topics.sh --create --topic $(TOPIC_NAME) --partitions 1 --replication-factor 1 --bootstrap-server $(KAFKA_BROKER)
	@seq 1 1000000 | docker-compose exec -T kafka kafka-console-producer.sh --bootstrap-server $(KAFKA_BROKER) --topic $(TOPIC_NAME)
	@echo "Завершено. Числа от 1 до 1000000 записаны в топик $(TOPIC_NAME)."
	@mvn clean package
	@java -jar target/TestKafka-1.0-SNAPSHOT-jar-with-dependencies.jar

stop:
	@docker-compose down
	@mvn clean
#@docker volume prune -f
clean:
	@docker image rm -f wurstmeister/zookeeper
	@docker image rm -f wurstmeister/kafka
	@rm output.txt