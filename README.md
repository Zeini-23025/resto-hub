# README.md

# Resto-Hub

Système de Gestion de Restaurant (Restaurant Management System)

This project implements a modular restaurant management system with multiple services.
It is designed for **backend microservices** and a **React + TypeScript frontend**.

---

## 🧩 Services

### 1. `menu-service`

* Manages menu items and prices
* Provides APIs to fetch menu information

### 2. `order-service`

* Handles customer orders (in-restaurant and delivery)
* Validates orders and triggers kitchen preparation

### 3. `kitchen-service`

* Manages kitchen workflow and preparation
* Receives order details after validation

---

## 🔗 Communication Flow

```text
Customer places an order
        ↓
order-service validates order
        ↓
kitchen-service receives order details for preparation
```

---

## ⚙️ Tech Stack

* Backend: Spring Boot (Java 17)
* Frontend: React + TypeScript + Vite
* Docker + Docker Compose for containerized development

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/resto-hub.git
cd resto-hub
```

### 2. Run with Docker Compose

```bash
docker compose up --build
```

* Frontend: [http://localhost:5173](http://localhost:5173)
* Backend services: [http://localhost:8080](http://localhost:8080) (menu, order, kitchen APIs)

---

## 📁 Project Structure

```
resto-hub/
├── backend/
│   ├── menu-service/
│   ├── order-service/
│   ├── kitchen-service/
├── frontend/
├── docker-compose.yml
├── README.md
└── .gitignore
```

---
