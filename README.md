# 🏦 Bank Branches Directory API

Banking Directory API is a REST API built with Spring Boot that manages the registration of bank branches along with their respective X and Y coordinates.

It allows users to store branch coordinates in an H2 database and perform queries that return the distance to registered branches, retrieved from a Redis cache or directly from the database when not cached.

When the cache is missing or expired, the data is fetched directly from the database, and a new cache is automatically created.

Cache entries are stored in JSON format for better readability and easier inspection through the Redis CLI.

---

## 🚀 Features

- Register and update bank branches
- Retrieve registered branches
- Query branches sorted by distance
- Caching mechanism using Redis for faster responses
- JSON serialization for human-readable cache entries
- Automatic cache refresh when expired or missing
- Basic authentication with preloaded users (admin/user)
- Automatic user initialization from application.yml via CommandLineRunner
- H2 console available at /h2-console for local development

---

## 🛠️ Tech Stack

- **Java 17+**
- **Maven**
- **Spring Boot 3+**
- ***Spring Security (Basic Auth)***
- **Spring Data JPA**
- **Spring Cache (Redis)**
- **GenericJackson2JsonRedisSerializer (JSON cache serialization)**
- **H2 (in-memory database)** 

---

## 📦 Project Structure
+ src/
  + main/
    + java/com/kls/banking/directory/api/
      + config
      + controller/
      + dao/
      + dto/
      + entity/
      + enums
      + dto/
      + mapper/
      + service/
      + utils/
    + resources/
      + application.yml

---

## 🔐 Authentication

The API uses HTTP Basic Authentication.

Default credentials are automatically created at application startup and loaded from application.yml:
```yaml
app:
  users:
    - username: admin
      password: admin
      role: ADMIN
    - username: user
      password: user
      role: USER
```

Each user is stored in the H2 database at startup through a CommandLineRunner component (UserInitializer).

Example authentication for testing (via Postman or curl):
```console
curl.exe -u user:user "localhost:8080/branches/health"
```
```console
curl.exe -u admin:admin "localhost:8080/branches/health"
```
🟡 Expected OK response:
```console
Healthy! All up and running!
```
---

## 🔒 Role-Based Authorization

The application implements role-based access control using Spring Security.

- ADMIN users can register new branches.
- USER and ADMIN roles can query branch distances.

Authentication is performed via Basic Auth with credentials stored in the database during application startup.

## ⚙️ Caching Details

The application uses Redis as the caching provider, with each cache entry having a configurable TTL (Time-To-Live) defined in application.yml.

Cached values are stored in JSON and can be inspected directly from Redis CLI.

---
🧱 Prerequisites

Before running the application, make sure Docker is installed and then follow the steps below to set up Redis.

1️⃣ Pull the Redis image:
```console
docker pull redis
```
2️⃣ Create and start a new container:
```console
docker run --name redis -p 6379:6379 -d redis
```
3️⃣ Access the container shell (optional):
```console
docker exec -it redis sh 
```
4️⃣ Test the connection with Redis CLI: (if running, server is expected to respond with PONG)
```console
redis-cli -h localhost -p 6379 ping
```
5️⃣ Exit the container shell:
```console
exit
```
---
🧪 Testing the Cache 

After running the application, you can test the caching behavior by following the steps below.

1️⃣ Make a GET request:
URL:
```yaml
localhost:8080/branches/distance?xCoord=-10.2&yCoord=4.5
```
🟡 Expected response (empty array, since no branches have been stored yet):
```yaml
{
  "branchesList": []
}
```

2️⃣ Open Redis CLI 
```console
docker exec -it redis redis-cli
```
3️⃣ List cached keys 
```console
keys branchesDistances*
``` 
🟡 Expected response:
```console
"branchesDistances::Coordinates(xCoord=-10.2, yCoord=4.5)"
```
Here, branchesDistances is the cache name, and
Coordinates(xCoord=-10.2, yCoord=4.5) is the cache key that Spring uses to store the data for the queried coordinates.

4️⃣ Check stored values 
```console
get "branchesDistances::Coordinates(xCoord=-10.2, yCoord=4.5)"
```
🟡 Expected response (no value yet associated with the cache key):
```console
(nil)
```
5️⃣ Add a branch via POST request 

URL:
```yaml
localhost:8080/branches
  ```
Body:

```yaml
{
  "branchName": "BRANCH_1",
  "posX": -5.2,
  "posY": 3
}
```
6️⃣ Run the GET request twice:
- On the first call, data is retrieved from the H2 database and stored in Redis.
- On the second call (before cache expiration), data is retrieved directly from Redis.

🟢 Expected response:
```yaml
{
  "branchesList": [
    {
      "branch": "BRANCH_1",
      "distance": 5.2201532544552744
    }
  ]
}
```
💡 Tip: After each GET request, you can also run 
```yaml
get "branchesDistances::Coordinates(xCoord=-10.2, yCoord=4.5)"
```
to confirm that the data has been cached in Redis. 

7️⃣ Check cache TTL

(The number returned represents the time remaining before expiration, in seconds.)
```console
ttl "branchesDistances::Coordinates(xCoord=-10.2, yCoord=4.5)"
```

---
## 🎓 About this Project

This project was developed as a study case to explore technologies such as Spring Boot, Spring Security, Redis Cache, and H2 Database.

Its purpose is to serve as a reference and learning resource for similar projects or future enhancements.

---
## 📄 License

This project is licensed under the MIT License.
You are free to use, modify, and distribute this software, provided that the original copyright notice is included.

For more information, refer to the [LICENSE](LICENSE) file.