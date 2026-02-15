# NextTick

**NextTick** is a high-performance Spring Boot side project for reserving the **nearest available time slot** with very low latency under high TPS conditions.

The project explores scalable reservation strategies using Redis priority queues, PostgreSQL persistence, and Gatling load testing to evaluate performance.

This is a side project built under a tight deadline. Some production-grade features are intentionally postponed.

# Features

- Reserve the closest available time slot efficiently
- High-throughput architecture targeting <100 ms latency
- Redis-backed priority queue using atomic operations
- PostgreSQL persistence with millions of records
- Queue rebuild strategy to recover from Redis crashes
- Scheduled rebuild to fix DB ↔ Redis inconsistencies
- Gatling-based performance benchmarking

## Architecture

- Spring Boot monolith
- Clean Architecture (clean-ish)
  - Separation of domain, application, and interface concerns
  - JPA repositories live in the application layer instead of infrastructure

This is a deliberate trade-off:
- Full leverage of Spring Data JPA
- Reduced boilerplate
- Avoidance of performance-impacting mapping layers

The design allows future evolution toward richer domain behavior such as events, async processing, and notifications.


### Reservation Flow

1. Client requests reservation
2. Redis priority queue returns nearest slot (ZPOPMIN)
3. Slot fetched from DB
4. Domain aggregate reserves slot
5. DB update with optimistic locking

### Queue Consistency Strategy

- On startup → rebuild queue from DB
- On Redis crash → rebuild automatically
- Scheduled rebuild → fix drift between DB and Redis

# Tech Stack

- Backend: Spring Boot (3.5.10)
- Database: PostgreSQL
- Queue: Redis Sorted Set (ZSET)
- Build: Gradle
- Container: Docker + docker-compose
- Load Testing: Gatling (in a separate gradle project)
- Language: Java 21

# Getting Started

## Requirements

- Docker
- Java 21
- Gradle (optional)

## Run with Docker

docker-compose up --build

Services started:

- PostgreSQL
- Redis
- NextTick App

## Run Locally

./gradlew build
java -jar backend/build/libs/app.jar

# REST API Overview

| Method | Endpoint | Description | Status      |
|--------|-----------|-------------|-------------|
| POST | /reservations | Reserve nearest available slot | Implemented |
| DELETE | /reservations/{id} | Cancel reservation | Implemented |

Example Response:

{
"id": 3371,
"holderId": 42
}

If no slot is available:

HTTP 204 No Content

# Testing Strategy

Performance testing is done using Gatling.

Goals:

- Measure latency under high TPS (Gatling)
- Validate Redis queue scalability
- Detect DB contention issues
- Compare queue vs DB-lock approach

Example test scenarios:

- Burst reservation traffic
- Parallel reservation + cancellation
- Redis failure recovery
- Queue rebuild performance

# Known Gaps and Planned Improvements

Because this is a side project with limited time, some features are missing:

## Security
- JWT authentication not implemented

## Error Handling
- Uniform error response model not designed
- Standard 40x error structure missing

## Validation
- Input validation can be expanded

## Observability
- Metrics dashboards
- Tracing
- Alerting


## Reliability
- Distributed lock improvements
- Backpressure strategy
- Slot expiration handling

# License
This project is currently unlicensed and intended for personal or educational use.

# Motivation

- High-TPS system design
- Redis priority queues
- Spring Boot scalability
- Distributed concurrency control
