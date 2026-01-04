# TasteBuds Catering System

TasteBuds Catering System is a Java-based application for managing catering orders, items, and customer data. It was implemented as an assignment for Quiz 2 of the Object-Oriented Concepts 2 (OOC2) course.

---

## Project Author & Course Information

- Author: Shaikh Muhaiminul Hasan  
- Student ID: 230042113  
- Assignment: Quiz 2  
- Course Code: SWE 4301  
- Course: OOC2

---

## Overview

This project demonstrates fundamental object-oriented design and file-based data handling for a small catering system. It includes functionality for creating and managing menus, taking orders, persisting data, and basic reporting. The implementation follows SOLID principles where applicable—specifically the Single Responsibility Principle (SRP) and the Open/Closed Principle (OCP)—and adheres to clean code practices.

---

## Features

- Order creation and tracking
- Customer information storage
- Persistence of data using XML files
- Simple console interface to interact with the system
- Unit testing with JUnit

---

## Technologies & Tools

- Java 21 (JDK 21)  
- Maven (build tool)  
- IntelliJ IDEA (recommended IDE)  
- XML (for file/data handling)  
- JUnit (for testing)

---

## Prerequisites

- JDK 21 installed and JAVA_HOME configured  
- Maven (3.x) installed  
- Recommended: IntelliJ IDEA for development and debugging

---

## Build & Run

From the project root:

1. Build the project with Maven:
   ```
   mvn clean package
   ```

2. Run from the command line (replace the JAR name with the actual artifact name produced under `target/`):
   ```
   java -jar target/tastebuds-catering-system-1.0.jar
   ```

Or run the application directly from IntelliJ by running the main class.

---

## Data & Configuration

- XML is used for persistence and sample data. Look for XML files under:
  - `src/main/resources/data/` directory (project-specific path).
- The application reads and writes XML files for storing orders, customer records, feedbacks and dirvers information. Ensure the application has read/write permissions for the directory used for XML files.

---

## Project Structure (typical)

- src/
  - main/
    - java/        (application source code)
    - resources/   (XML files, config, sample data)
  - test/
    - java/        (unit tests)

Adjust paths according to the actual repository layout.

---

## Notes

- SOLID principles (SRP, OCP) and clean code practices were followed where appropriate.  
- JUnit was used for testing.  
- XML was chosen for file and data handling to demonstrate structured persistence without a database.

---

## License

This repository is provided for educational purposes. 

---
