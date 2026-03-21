# Software Architecture: Ticketing System (Microservices)

[![Java](https://img.shields.io/badge/Java-21%2B-blue)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-latest-brightgreen)](https://spring.io/projects/spring-boot)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20%7C%20DDD%20%7C%20Hexagonal-blueviolet)]()
[![Status](https://img.shields.io/badge/status-in%20progress-yellow)]()

## 📚 About

This repository documents my study and practical implementation based on the book:

**"Arquitetura completa de software: Do desenvolvimento à maestria" — Gustavo Luca Brandão**

The goal is to evolve from coding-focused development to designing **complete software architectures**, applying concepts in a real-world inspired system: a **ticket sales platform**.

The project covers the full lifecycle of software architecture, including:

- Clean Code and SOLID principles
- Architectural patterns (Layered, Hexagonal, Clean Architecture)
- Domain-Driven Design (DDD)
- REST API maturity (The Glory of REST)
- Microservices architecture
- Event-driven architecture (EDA)
- Security (OAuth2, Keycloak)
- Observability and Kubernetes

All concepts are applied incrementally in a **hands-on microservices system**.

---

## 🧠 System Overview

The system simulates a **ticket sales platform**, supporting:

### Customer Perspective
- Ticket selection
- Order reservation
- Checkout and payment (credit card, boleto, Pix)

### Admin Perspective
- Event and ticket management
- Payment monitoring

This project is designed to reflect real-world complexity and architectural decision-making. :contentReference[oaicite:0]{index=0}

---

## 📋 Progress

### 🧩 Fundamentals & Best Practices

- [ ] **Chapter 1:** Clean Code and SOLID
- [ ] **Chapter 2:** Architectural Patterns (DDD, Hexagonal, Clean Architecture, Layered)
- [ ] **Chapter 3:** REST API Maturity (The Glory of REST)

### 🏗️ Applying Architecture (Ticketing System)

- [ ] **Chapter 4:** Deriving Requirements with DDD
- [ ] **Chapter 5:** Microservices Architecture Definition
- [ ] **Chapter 6:** Event Management (event-service, room-service, ticket-service)
- [ ] **Chapter 7:** Order Reservation (order-service, customer-service, BFF, CORS)

### ⚙️ Advanced Architecture

- [ ] **Chapter 8:** Docker and Docker Compose
- [ ] **Chapter 9:** Event-Driven Architecture with Kafka
- [ ] **Chapter 10:** Checkout and Payment Security (AES, adapters)
- [ ] **Chapter 11:** Payment Processing (EDA integration)

### 🔐 Security

- [ ] **Chapter 12:** Authentication and Authorization with OAuth2 & Keycloak
- [ ] **Chapter 13:** Admin Flow and Access Control

### 📊 Observability & Infrastructure

- [ ] **Chapter 14:** Observability and Kubernetes
- [ ] **Chapter 15:** References and Next Steps

---

## 🧱 Architecture Goals

- Apply **Clean Architecture** and **DDD** in a real system
- Design loosely coupled and scalable microservices
- Implement **event-driven communication**
- Ensure **security by design**
- Build a production-ready mindset (observability, containerization, orchestration)

---

## 🚀 Getting Started

Details will be added progressively as each module is implemented.

---

## 📝 Notes

This repository is part of a continuous learning process focused on:

- Software Architecture mastery
- Real-world system design
- Technical leadership preparation

---

## 📄 License

This project is for educational purposes. All rights to the book content belong to the author Gustavo Luca Brandão and Casa do Código.
