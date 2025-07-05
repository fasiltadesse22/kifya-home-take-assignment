# Payment Order Gateway Service - Kifya Home Take Assignment

A high-performance, fault-tolerant Spring Boot microservice for processing and orchestrating payments across multiple external providers. Designed for scalability, idempotency, and durability.

![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring--Boot-3.4.4-brightgreen)

---

## Features
- Integrates with multiple payment providers
- Idempotent request handling
- Guarantee at least once delivery to the external provider
- Allow clients to track payment status by payment-order-id
- Facilitate provider extensiblity
- Kafka integration for async workflows
- Notify(Emit domain events) the status of payment-order (COMPLETED or FAILED) to the downstream systems

---

## Tech Stack

- Java 21
- Spring Boot 3.4.4
- PostgreSQL
- Kafka
- Docker

---

## Getting Started

### Prerequisites

- Java 21+
- Docker
- PostgreSQL
- Kafka

### Project Structure
![Screenshot from 2025-07-05 13-53-39](https://github.com/user-attachments/assets/d85eb13e-1f85-409b-a20c-67afe6e9bc1a)


### Clone & Run

```bash
git clone https://github.com/fasiltadesse22/kifya-take-home-assignment.git
cd kifya-take-home-assignment/infrastructure/docker-compose
sudo docker-compose -f common.yml -f zookeeper.yml up       # starts zookeeper
sudo docker-compose -f common.yml -f kafka_cluster.yml up   # runs kafka cluster
sudo docker-compose -f common.yml -f init_kafka.yml up      # Creates the Kafka topic. This only needs to be run once.

# Similar to what I did for Kafka, I'll provide a docker-compose configuration for the 'payment-order-service' and 'Postgres'.

- Install Postgres locally and cofigure it with user = 'postgres' and password = 'admin'
cd kifya-take-home-assignment/payment-order-service/payment-order-container
sudo mvn clean install
sudo java -jar target/payment-order-container-1.0-SNAPSHOT.jar
```

### API Endpoints

| Method | Endpoint                            | Description                 |
| ------ | ----------------------------------- | --------------------------- |
| POST   | `/payment-orders`                   | Create payment order        |
| GET    | `/payment-orders/{paymentOrderId}`  | Track payment order status  |
