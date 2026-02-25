# 🚀 TradeFlow - Scalable E-Commerce Backend API

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)
![Redis](https://img.shields.io/badge/Redis-Caching-red.svg)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Event%20Driven-black.svg)
![Docker](https://img.shields.io/badge/Docker-Compose-blue.svg)

**TradeFlow** is a production-ready, high-performance, event-driven E-Commerce Backend API.
It leverages asynchronous messaging, distributed caching, and a flexible tagging architecture to provide scalable, industry-standard solutions.

---

## 📡 API Endpoints

### 🔐 Authentication
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/register` | Register new user | ❌ |
| POST | `/authenticate` | Login & receive JWT | ❌ |
| POST | `/refreshToken` | Refresh access token | ❌ |
| GET | `/getUser` | Get current user info | ✅ |

### 📦 Orders
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/rest/api/order/create` | Create a new order | ✅ |
| GET | `/rest/api/order/{id}` | Get order by ID | ✅ |
| GET | `/rest/api/order/my-orders` | Get current user's orders | ✅ |
| PUT | `/rest/api/order/admin/update-status/{id}` | Update order status | ✅ Admin |
| GET | `/rest/api/order/admin/all` | Get all orders (paginated) | ✅ Admin |

### 🛍️ Products
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/rest/api/product/add` | Add new product | ✅ Admin |
| GET | `/rest/api/product/all` | Get all products | ❌ |
| GET | `/rest/api/product/filter` | Filter products by categories | ❌ |
| DELETE | `/rest/api/product/delete/{id}` | Delete product | ✅ Admin |

### 🗂️ Categories
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/rest/api/category/add` | Add new category | ✅ Admin |
| GET | `/rest/api/category/all` | List all categories | ❌ |
| DELETE | `/rest/api/category/delete` | Delete category | ✅ Admin |

### 🛒 Cart
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/rest/api/cart/add` | Add item to cart | ✅ |
| GET | `/rest/api/cart` | View cart | ✅ |
| DELETE | `/rest/api/cart/delete` | Clear cart | ✅ |

### 💰 Wallet
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/rest/api/wallet/deposit` | Deposit balance | ✅ |
| GET | `/rest/api/wallet/my-wallet` | Get wallet info | ✅ |

### ❤️ Favorites
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/rest/api/favorite/toggle` | Toggle favorite product | ✅ |
| GET | `/rest/api/favorite/myfavorites` | Get favorite products | ✅ |

### 📊 Dashboard
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/rest/api/dashboard/summary` | Get sales summary | ✅ Admin |
| GET | `/rest/api/dashboard/chart-data` | Get chart data | ✅ Admin |

---

## 📦 Response Structure

All API responses follow a unified wrapper format:
```json
{
  "result": true,
  "errorMessage": null,
  "data": { }
}
```

### Order Response
```json
{
  "result": true,
  "errorMessage": null,
  "data": {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "orderNumber": "ORD-A1B2C3D4",
    "status": "APPROVED",
    "totalAmount": 299.99,
    "address": "123 Main Street",
    "orderItemList": [
      {
        "product": {
          "name": "Gaming Laptop",
          "price": 299.99,
          "categories": [{ "name": "Electronics" }]
        },
        "quantity": 1,
        "priceAtPurchase": 299.99
      }
    ],
    "user": {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@example.com",
      "role": "USER",
      "wallet": {
        "balance": 700.01,
        "currency": "USD"
      }
    },
    "createdAt": "2026-02-25T04:52:50.115Z"
  }
}
```

### Product Response
```json
{
  "result": true,
  "errorMessage": null,
  "data": {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "name": "Gaming Laptop",
    "description": "High performance laptop",
    "price": 299.99,
    "stock": 50,
    "totalSalesCount": 120,
    "totalEarning": 35998.80,
    "categories": [{ "name": "Electronics" }],
    "images": [{ "imageUrl": "https://..." }]
  }
}
```

### Error Response
```json
{
  "result": false,
  "errorMessage": {
    "status": 404,
    "exception": {
      "hostName": "tradeflow-server",
      "path": "/rest/api/order/unknown-id",
      "createTime": "2026-02-25T04:52:50.115Z",
      "message": "No record exists for given id"
    }
  },
  "data": null
}
```

---

## 🔄 Order Flow
```
Client → POST /rest/api/order/create
              ↓
        OrderService
        - Validates stock availability
        - Validates wallet balance
        - Deducts wallet balance
        - Clears cart
              ↓
        PostgreSQL (persists order)
              ↓
        ApplicationEventPublisher → OrderEventListener
              ↓
        Kafka Producer → order-create topic
              ↓
        Kafka Consumer → Email Notification
```

---

## 🏗️ Architecture & Design Decisions

### 1. Event-Driven Architecture (Apache Kafka)
Order creation and notification systems are loosely coupled.
When an order is placed, an event is published to the `order-create` topic.
Consumer services asynchronously handle email notifications without blocking the main thread.

### 2. High-Performance Caching (Redis)
Frequently accessed data such as product lists is cached in Redis.
Implemented `Write-Through` and `Cache-Eviction` policies using Spring's `@Cacheable`.

### 3. Dynamic Filtering & Tagging Architecture
**Challenge:** Filtering products that match ALL selected categories simultaneously.
**Solution:** Custom JPQL query using `GROUP BY` and `HAVING COUNT(DISTINCT)` for true intersection filtering.

### 4. API Rate Limiting
Implemented `RateLimitingService` using Bucket4j to protect against brute-force attacks and traffic spikes.

### 5. Aspect-Oriented Logging (AOP)
Custom `LoggingAspect` automatically captures method calls, request details, and execution times across all service layers without polluting business logic. Logs are stored in rolling daily files under the `logs/` directory.

### 6. Global Exception Handling
Centralized `GlobalExceptionHandler` using `@RestControllerAdvice` catches all exceptions and returns consistent, structured error responses. Custom `BaseException` and `MessageType` enum provide type-safe error messaging across the entire application.

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3.x |
| Database | PostgreSQL |
| Caching | Redis |
| Messaging | Apache Kafka |
| Security | Spring Security + JWT |
| Mapping | MapStruct |
| Logging | Logback + AOP |
| Testing | JUnit 5, Mockito, Testcontainers |
| Documentation | OpenAPI / Swagger UI |
| Containerization | Docker Compose |

---

## 📁 Project Structure
```
src/main/java/com/fatihsengun/
├── aspect/          # AOP logging
├── config/          # Security, Redis, Swagger, Web configs
├── controller/      # REST controllers (interface + impl)
├── dto/             # Request/Response DTOs
├── entity/          # JPA entities
├── enums/           # OrderStatus, RoleType
├── event/           # Spring application event listeners
├── exception/       # Custom exception classes
├── handler/         # Global exception handler
├── jwt/             # JWT filter and service
├── kafka/           # Producer, consumer, config
├── mapper/          # MapStruct global mapper
├── repository/      # Spring Data JPA repositories
├── service/         # Service interfaces + implementations
└── starter/         # Main application class
```

---

## ⚙️ Prerequisites
- Docker & Docker Compose
- Java 21

---

## 🚀 Getting Started
```bash
git clone https://github.com/fatihsenguun/springboottradeflow.git
docker-compose up -d
./mvnw spring-boot:run
open http://localhost:8080/swagger-ui/index.html
```

---


## 🧪 Testing

- **Unit Tests** — Service layer tested with JUnit 5 & Mockito
- **Integration Tests** — Repository layer tested with Testcontainers (real PostgreSQL instance)
```bash
./mvnw test
```