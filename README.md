
# 💇 Book My Salon – Salon Appointment Booking System

A **microservices-based salon appointment booking platform** built with Spring Boot, React.js, and Docker to enable seamless real‑time booking, secure authentication, and online payments.

---

## 🧱 Project Overview

Inspired by modern full-stack salon systems with microservices architecture, this project offers modular services for users, styling staff, appointments, and payments. Secure authentication is managed with Keycloak and JWT, while RabbitMQ enables asynchronous messaging and real-time notifications.

---

## 🚀 Tech Stack

### Backend
- Spring Boot (Java)
- Spring Cloud Gateway / Eureka (API Gateway & Service Discovery)
- Keycloak + JWT for authentication
- RabbitMQ for asynchronous messaging
- WebSocket for real-time updates (if implemented)
- MySQL / H2 database
- JUnit for unit tests
- SonarQube for code quality
- Swagger for interactive API docs

### Frontend
- React.js
- Tailwind CSS (or Material-UI)
- Formik for forms handling
- Axios for HTTP requests
- Redux (optional) for state management
- React Router

### DevOps & Infrastructure
- Docker & Docker Compose for containerization and orchestration
- Razorpay (and/or Stripe) for secure payment integration

---

## 🔧 Features

- Secure user registration, login (JWT via Keycloak)
- Role-based access control (Customer, Admin, Staff)
- Booking management: schedule, view, cancel appointments
- Service & staff management dashboards
- Payment gateway integration (Razorpay/Stripe)
- Notifications via RabbitMQ and WebSockets
- Modular microservices for scalability and maintenance

---

## 📂 Repository Structure

```
Salon_Appointment_Booking/
├── backend/
│   ├── user-service/
│   ├── appointment-service/
│   ├── payment-service/
│   ├── stylist-service/ (or service-offering)
│   ├── api-gateway/
│   └── discovery-server/
└── frontend/
    ├── public/
    └── src/
        ├── components/
        ├── pages/
        ├── services/
        └── utils/
├── docker-compose.yml
└── README.md
```

---

## ⚙️ Setup Guide

### Prerequisites
- Java 17+
- Node.js 16+
- Docker & Docker Compose
- MySQL (or H2 for development)
- Keycloak (via container or local install)
- RabbitMQ (via Docker or remote)

### Setup Steps

1. **Clone the Repository**

   ```bash
   git clone https://github.com/amanmathankar13/Salon_Appointment_Booking.git
   cd Salon_Appointment_Booking
   ```

2. **Start backend microservices and dependencies**

   ```bash
   cd backend
   mvn clean install
   docker-compose up -d  # includes Keycloak, RabbitMQ, and microservices
   ```

3. **Run frontend**

   ```bash
   cd ../frontend
   npm install
   npm start
   ```

4. **Visit the app**

   - Frontend: `http://localhost:3000`
   - Swagger UI: `http://localhost:8080/swagger-ui.html` (adjust port per gateway proxy)
   - Keycloak console: `http://localhost:8080/auth`

---

## 🧪 Testing

- Backend services are unit-tested using JUnit.
- Run all tests with:

  ```bash
  mvn test
  ```

- Aim for 85%+ code coverage.

---

## 🧠 Future Enhancements

- Email/SMS notifications for booking confirmations
- User review & rating system for stylists
- Support for real-time chat or reminders using WebSocket
- Detailed analytics dashboard for admins
- Mobile or hybrid client using React Native or Flutter

---

## ⚖️ License & Contributions

**License:** This project is released under the MIT License.

Contributions 🌟 are welcome! Feel free to fork, open issues, or submit pull requests. If you find it useful, don’t forget to ⭐ the repo.

---

## 🙋 Author

**Aman Mathankar**  
📧 [mathankaraman13@gmail.com](mailto:mathankaraman13@gmail.com)  
🌐 [LinkedIn](https://www.linkedin.com/in/aman-mathankar-838b39287)  
💻 [GitHub](https://github.com/amanmathankar13)
