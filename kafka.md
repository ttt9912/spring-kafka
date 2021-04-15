# Tutorial

https://github.com/confluentinc/cp-docker-images/wiki/Getting-Started

# Start Kafka

`docker-compose up`

docker-compose.yml
from https://github.com/confluentinc/cp-docker-images/blob/master/examples/kafka-single-node/docker-compose.yml

# Kafka Tools

https://kafka.apache.org/quickstart

connect to kafka and go to /bin

`docker exec -it ttt-kafka bash`

`cd /bin`

---

### Create Topic

`kafka-topics --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic strings`

alternative: programmatically via spring

---

### List Topics

`kafka-topics --list --zookeeper zookeeper:2181`

### Topic Details

`kafka-topics --describe --topic strings --zookeeper zookeeper:2181`

### Delete Topic

`kafka-topics --delete --zookeeper zookeeper:2181 --topic strings`

---

### Console Consumer

`kafka-console-consumer --bootstrap-server kafka:9092 --topic strings --from-beginning`

---

### Show Consumer Groups

`kafka-consumer-groups --list --bootstrap-server kafka:9092`

### Consumer Group Details

`kafka-consumer-groups --describe --group strings-filtered --bootstrap-server kafka:9092`
