# 📌 Spring Boot with MongoDB - Practice Project

## 📖 Overview
This project is a test application that connects a backend developed in Spring Boot with a MongoDB database. The main goal is to explore the different interaction possibilities between Spring Boot and MongoDB, without additional authentication or security.

---

## 🐳 Docker-Based Setup (Recommended)

To simplify environment setup, Docker is used to containerize both the MongoDB database and the Spring Boot application.

### 📦 Files Included
- `Dockerfile` (Spring Boot App)
- `/db-data/Dockerfile` (MongoDB Setup)
- `docker-compose.yml` (Orchestrates both services)

### ▶️ Building and Running Containers
To build the images and start the containers for the first time, run:
```sh
docker-compose up --build
```
This command builds both images and starts the services defined in `docker-compose.yml`.

### 💻 Accessing Containers
- **MongoDB Shell:**
  To access the MongoDB terminal inside the container:
  ```sh
  docker exec -it mongo_db_online_shop mongo
  ```
- **Spring Boot App Terminal:**
  To access the Spring Boot container terminal:
  ```sh
  docker exec -it springboot_online_shop_app /bin/zsh
  ```

### 🛑 Stopping Containers
To gracefully stop all running containers:
```sh
docker-compose stop
```

---

## 🛠️ Database Setup (Alternative to Docker)

### 🏗️ Installing MongoDB Locally (macOS via Homebrew)
MongoDB can also be installed locally using Homebrew. You can manage it using:
- **Start MongoDB:**
  ```sh
  brew services start mongodb-community
  ```
- **Stop MongoDB:**
  ```sh
  brew services stop mongodb-community
  ```

### 📂 Database Initialization
A script `init_mongo.js` (located in `db-data/`) is used to initialize the database with collections: `customers`, `products`, `orders`, and `facturers`.

#### ▶️ Running the Initialization Script
```sh
mongo < init_mongo.js
```

### 📜 Generating JSON Collection Structures
Use the provided Python script to generate JSON representations of each collection’s structure:
```sh
python3 get_collection_attrs.py dbName dbCollection
```
Repeat this for each collection.

---

## 🚀 Backend Development with Spring Boot

The application uses two primary approaches for MongoDB interaction:

### 📌 1. MongoRepository
Ideal for basic CRUD operations.

Example:
```java
public interface CustomerRepository extends MongoRepository<Customer, String> {
    List<Customer> findByName(String name);
}
```

### 📌 2. MongoTemplate
Used for advanced queries and aggregations.

Example – Filtering:
```java
Query query = new Query();
query.addCriteria(Criteria.where("address.city").is("New York"));
return mongoTemplate.find(query, Customer.class);
```

Example – Aggregation:
```java
Aggregation aggregation = Aggregation.newAggregation(
    Aggregation.match(Criteria.where("status").is("ACTIVE")),
    Aggregation.group("city").count().as("customerCount")
);
```

---

## 🏃 Running the Spring Boot Application (Without Docker)
1. Make sure MongoDB is running.
2. Run the Spring Boot app:
   ```sh
   mvn spring-boot:run
   ```
3. API will be accessible at: `http://localhost:9090/`

---

## 🔬 Testing with Postman
Postman collection file: `Online_Shop_MongoDB.json`

### 📥 Import Instructions
1. Open Postman.
2. Go to **File > Import**.
3. Choose the JSON file.
4. Access and test API endpoints directly.

---

## 🎯 Conclusion
This project provides a comprehensive environment to explore how Spring Boot interacts with MongoDB. It supports both local and containerized setups, uses different repository patterns (MongoRepository, MongoTemplate, Aggregation), and includes tools like Postman and Docker for easier development and testing workflows.