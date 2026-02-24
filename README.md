# 🚀 TradeFlow - Scalable E-Commerce Backend API



![Java](https://img.shields.io/badge/Java-21-orange.svg)

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green.svg)

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)

![Redis](https://img.shields.io/badge/Redis-Caching-red.svg)

![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Event%20Driven-black.svg)

![Docker](https://img.shields.io/badge/Docker-Compose-blue.svg)



**TradeFlow** is a production-ready, high-performance, and **event-driven** E-Commerce Backend API designed with a **Microservices-first** mindset.



Unlike traditional monolithic CRUD applications, TradeFlow leverages **asynchronous messaging**, **distributed caching**, and a flexible **tagging architecture** to provide scalable and industry-standard solutions.



---



## 🏗️ Architecture & Design Decisions



### 1. Event-Driven Architecture (Apache Kafka)

Order creation and notification systems are loosely coupled.

- **Scenario:** High traffic should not block the main thread during order processing.

- **Solution:** When an order is placed, an event is published to the `order-create` topic. Consumer services asynchronously handle stock reduction and notifications.



### 2. High-Performance Caching (Redis)

Frequently accessed but rarely changed data (e.g., Product Lists) is cached in Redis.

- **Strategy:** Implemented `Write-Through` and `Cache-Eviction` policies using Spring's `@Cacheable`.



### 3. Dynamic Filtering & Tagging Architecture (Advanced JPQL)

- **Challenge:** Filtering products that meet **ALL** selected criteria (Intersection).

- **Solution:** A custom JPQL query utilizing `GROUP BY` and `HAVING COUNT` was implemented.



### 4. API Rate Limiting & Request Counting

- **Challenge:** Protecting the API from brute-force attacks and managing heavy traffic spikes.

- **Solution:** Implemented a robust request counting system (`RateLimitingService`) to track and throttle incoming requests, ensuring fair usage and system stability.



### 5. Aspect-Oriented Logging (AOP)

- **Challenge:** Keeping business logic clean while maintaining deep observability into system behavior.

- **Solution:** Leveraged a custom `LoggingAspect` to automatically capture, trace, and log request details, execution times, and application states across the platform without cluttering the core services.



---



## 🛠️ Tech Stack

* **Core:** Java 21, Spring Boot 3.x

* **Database:** PostgreSQL

* **Caching:** Redis

* **Message Broker:** Apache Kafka

* **Security:** Spring Security, JWT

* **Documentation:** OpenAPI (Swagger UI)



---



## 🚀 Installation

1. `git clone https://github.com/fatihsenguun/tradeflow.git`

2. `docker-compose up -d`

3. `./mvnw spring-boot:run`