# Spring Cloud Stream - Kafka - Docker
### Kafka with Spring Cloud Stream on Docker containers

This repository demonstrates Kafka message broker (send/receive messages) functionality using Spring Cloud Stream on Docker containers. Objects are sent and received between microservices with Kafka

1. The application contains two microservices named `Projects` and `ChargeCodes` with a `gateway` named gateway.
2. To simplify development and deployment in docker, docker-compose setup process was used.
3. When new project created in Projects microservice, Kafka sends a message to Broker and ChargeCodes microservice consumes the message, creates new charge code with project name in it.
4. Both the microservices are not known to each other and no dependancy exists between two.
