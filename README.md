# 🏦 Banking Directory API

**Banking Directory API** is a REST API built with **Spring Boot** that manages the registration of bank branches along with their respective X and Y coordinates.  
It allows users to perform queries that return the distance to the registered branches, retrieved from a cache.  
When the cache has expired, the data is fetched directly from the database, and a new cache is automatically created.

---

## 🚀 Features

- Register and update bank branches
- Retrieve registered branches
- Query branches sorted by distance
- Caching mechanism for faster responses
- Automatic cache refresh upon expiration

---

## 🛠️ Tech Stack

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **Redis**
- **Maven**
- **H2** 

---

## 📦 Project Structure
