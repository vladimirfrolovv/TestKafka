.PHONY: start stop

all: start

TOPIC_NAME="sequence"
KAFKA_BROKER="localhost:9092"

start:
	#./gradlew build
	docker compose up -d

stop:
	@docker compose down
	#./gradlew clean
	#docker volume prune -f
	#docker system prune -a
topic:
	@docker compose exec kafka kafka-topics.sh --create --topic $(TOPIC_NAME) --bootstrap-server $(KAFKA_BROKER)
write:
	@#docker compose exec kafka kafka-console-producer.sh --topic $TOPIC_NAME --bootstrap-server $(KAFKA_BROKER)
	#seq 1 1000000 | docker compose exec kafka -T kafka-console-producer.sh --broker-list --bootstrap-server $(KAFKA_BROKER) $(KAFKA_BROKER) --topic $(TOPIC_NAME)
	seq 1 1000000 | docker-compose exec -T kafka kafka-console-producer.sh  --bootstrap-server 	$(KAFKA_BROKER) --topic $(TOPIC_NAME)


	@echo "Завершено. Числа от 1 до 1 миллиона записаны в топик $(TOPIC_NAME)."
read:
	@docker compose exec kafka kafka-console-consumer.sh --topic $(TOPIC_NAME) --bootstrap-server $(KAFKA_BROKER) --consumer-property auto.offset.reset=earliest