.PHONY: start stop

all: start

TOPIC_NAME=sequence
KAFKA_BROKER=localhost:9092

start:
	docker-compose up -d

stop:
	@docker-compose down
	docker volume prune -f
	docker system prune -a
topic:
	@docker-compose exec kafka kafka-topics.sh --create --topic $(TOPIC_NAME) --bootstrap-server $(KAFKA_BROKER) --partitions 1 --replication-factor 1
write:
	seq 1 1000000 | docker-compose exec -T kafka kafka-console-producer.sh  --bootstrap-server 	$(KAFKA_BROKER) --topic $(TOPIC_NAME)
	@echo "Завершено. Числа от 1 до 1 миллиона записаны в топик $(TOPIC_NAME)."
read:
	@docker-compose exec kafka kafka-console-consumer.sh --topic $(TOPIC_NAME) --bootstrap-server $(KAFKA_BROKER) --consumer-property auto.offset.reset=earliest

list-topics:
	@docker-compose exec kafka kafka-topics.sh --bootstrap-server $(KAFKA_BROKER) --list

groups:
	@docker compose exec kafka kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-consumer-group --describe