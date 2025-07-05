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

---

## Design and Architecture

### Hexagonal Architecture
I apply **Hexagonal Architecture**; a software design pattern that distinctly separates the core-business-logic (**payment-order-domain** package) from external systems like APIs, databases, messaging queues, and the user interface.

![image](https://github.com/user-attachments/assets/492c327b-4a1a-4c45-8878-05c16fccdfdb)

---

## How Architecturel Cahllenges Were Solved

### Concurrency and Rate Limiting

***Problem***: How does your design enforce the global 2 TPS limit, especially if the system were to be scaled horizontally (multiple running instances)? How do you prevent race conditions in the payment processing flow?

***Solution***: I resolved the issue by configuring the Kafka consumer (inbound adapter) with the following settings:
- max.poll.records=2
- max.poll.interval.ms=1000

### State Management and Durability:

***Problem***: How and where is the state of a payment stored to guarantee durability and consistency through the entire lifecycle? How does your idempotency key management work, and how does it survive restarts?

***Solution***: The PaymentOrderStatus is stored in the payment_orders table in the PostgreSQL database to ensure that the status of each payment is persisted, even in the event of a database or application server failure or restart.
To guarantee idempotent behavior, the application expects an idempotency key from the client, which is stored alongside the payment order record. If a request with the same idempotency key is received again, the application detects the duplicate and avoids reprocessing it.
Since the idempotency key is persisted in the database, this protection remains effective across restarts, ensuring consistency and preventing duplicate processing.

### Decoupling and Extensibility:

***Problem***: How did you apply design patterns (e.g., Strategy, Adapter) or architectural styles (e.g., Hexagonal/Ports & Adapters) to meet the requirement for provider extensibility and to keep business logic separate from infrastructure concerns??

***Solution***: As mentioned above and illustrated in the architectural diagram, I adopted the Hexagonal Architecture (also known as Ports and Adapters) to address concerns around decoupling, extensibility, and separation of concerns. This approach ensures that core business logic remains isolated from infrastructure-related components.
The diagram demonstrates how the business domain module is independent of both the event publisher and data access modules. This separation provides the flexibility to extend the service easily for example, to integrate with new payment providers — without impacting the core logic.

### Reliability and Failure Modes:

***Problem***: What happens in your system when dependencies fail? The database? The message queue (if any)? How does your retry and event emission logic play together to ensure at-least-once semantics for both provider delivery and event publication?

***Solution***: Let me discuss each one of the scenarios one by one
- ___Database Failure___: Since the payment order is stored in the table even if the database is failed and restat we dont loose any data and the system will contue from where it stops
- ___The message Queue Failure___: I configured Kafka with volume mapping (persistent storage) to ensure data durability across restarts or failures. Specifically, I mounted Kafka's data directories to persistent volumes on the host machine.
This setup guarantees that Kafka's commit logs, topic partitions, and message offsets are not lost when the Kafka broker restarts or crashes. As a result, the system maintains reliable message delivery and processing even in the event of unexpected failures, supporting durability guarantees such as "at least once" or "exactly once" delivery semantics.
![image](https://github.com/user-attachments/assets/1ca946af-aabd-44ca-bd2e-caf2979b951c)



    



