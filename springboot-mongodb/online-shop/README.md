# 📌 Spring Boot with MongoDB - Practice Project

## 📖 Overview
This project is a test application that connects a backend developed in Spring Boot with a MongoDB database. The main goal is to explore the different interaction possibilities between Spring Boot and MongoDB, without additional authentication or security.

## 🛠️ Database Setup
### 🏗️ Installing MongoDB
MongoDB has been installed using Homebrew on macOS. You can manage the MongoDB instance with the following commands:

- **Start MongoDB:**
  ```sh
  brew services start mongodb-community
  ```
- **Stop MongoDB:**
  ```sh
  brew services stop mongodb-community
  ```

### 📂 Database Initialization
A script `init_mongo.js`, allocated in `db-data/`, is provided to populate the database with sample data. This script creates the required collections (`customers`, `products`, `orders`, `facturers`) and inserts example documents.

#### ▶️ Running the Initialization Script
Execute the following command in the terminal:
```sh
mongo < init_mongo.js
```

### 📜 Generating JSON Files for Collection Structures
A Python script (`get_collection_attrs.py`) is included to create JSON files representing the structure of each collection. This script should be run after initializing the database.

#### ▶️ Running the Python Script
To generate the JSON for each collection, execute:
```sh
python3 get_collection_attrs.py dbName dbCollection
```
This process should be repeated for each collection.

---

## 🚀 Backend Development with Spring Boot
The application is built using Spring Boot and MongoDB, leveraging different approaches for database interaction:

### 📌 Using Repositories
#### 1️⃣ MongoRepository (Standard Repository)
MongoRepository is used for basic CRUD operations. It provides built-in methods such as:
- `save(entity)` → Insert or update a document.
- `findById(id)` → Retrieve a document by ID.
- `findAll()` → Fetch all documents.
- `deleteById(id)` → Remove a document by ID.

Example:
```java
public interface CustomerRepository extends MongoRepository<Customer, String> {
    List<Customer> findByName(String name);
}
```
This allows performing queries with minimal effort using method naming conventions.

#### 2️⃣ Custom Repositories with MongoTemplate
For more complex queries, filtering, and aggregations, `MongoTemplate` is used. This provides greater flexibility compared to `MongoRepository`.

Example using `MongoTemplate` and `Criteria`:
```java
@Service
public class CustomerService {
    private final MongoTemplate mongoTemplate;
    
    public CustomerService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    public List<Customer> findCustomersByCity(String city) {
        Query query = new Query();
        query.addCriteria(Criteria.where("address.city").is(city));
        return mongoTemplate.find(query, Customer.class);
    }
}
```

#### 3️⃣ Aggregation Operations
Aggregation queries allow processing and transforming data before returning results.

Example using `Aggregation`:
```java
Aggregation aggregation = Aggregation.newAggregation(
    Aggregation.match(Criteria.where("status").is("ACTIVE")),
    Aggregation.group("city").count().as("customerCount")
);
List<Document> results = mongoTemplate.aggregate(aggregation, "customers", Document.class).getMappedResults();
```
This enables advanced data processing directly in MongoDB before retrieving results in Spring Boot.

---

## 🏃 Running the Spring Boot Application
### ▶️ Steps to Run Locally
1. Ensure MongoDB is running.
2. Compile and run the Spring Boot application:
   ```sh
   mvn spring-boot:run
   ```
3. The API will be available at `http://localhost:9090/`.

---

## 🔬 Testing with Postman
A Postman collection is included (`Online_Shop_MongoDB.json`), containing CRUD endpoints for all collections.

### 📥 Importing the Postman Collection
1. Open Postman.
2. Navigate to **File > Import**.
3. Select the provided JSON file.
4. Once imported, you can test the API endpoints directly.

---

## 🎯 Conclusion
This project serves as a hands-on environment for exploring Spring Boot and MongoDB interactions, using different repository approaches and making testing easier with Postman.

