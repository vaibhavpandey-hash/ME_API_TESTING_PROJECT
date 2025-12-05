📘 API Testing Project

This project contains automated API test cases designed to validate multiple REST API endpoints using industry-standard testing frameworks and best practices. The goal is to ensure the reliability, accuracy, and stability of backend services through structured and repeatable tests.

🚀 Features

✔ Automated API test execution

✔ Validation of response status codes, headers, and payloads

✔ Positive & negative test scenarios

✔ Modular test structure for easy scalability

✔ Reusable utilities for requests and assertions

✔ Environment-based configuration

🧰 Tech Stack

Java

RestAssured

TestNG

Gradle

JSON / Schema Validation

📂 Project Structure
src/
 ├── test/
 │    ├── java/
 │    │     ├── tests/        → Test classes
 │    │     ├── utils/        → Helper methods
 │    │     └── data/         → Test data
 └── main/
      └── java/               → Core utilities (if any)

▶️ How to Run Tests

Install dependencies:

gradle clean build


Run all tests:

gradle test

🎯 Purpose

This project demonstrates API testing skills including:

Request building

Response validation

Chaining API calls

Error handling

Automation best practices
