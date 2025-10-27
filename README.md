# 🏦 Banking Directory API

Banking Directory API is a REST API built with Spring Boot that manages the registration of bank branches along with their respective X and Y coordinates.

It allows users to perform queries that return the distance to the registered branches, retrieved from a Redis cache.

When the cache is missing or expired, the data is fetched directly from the database, and a new cache is automatically created.

The cache entries are serialized in JSON format for improved readability and easier inspection through the Redis CLI.

---

## 🚀 Features

- Register and update bank branches
- Retrieve registered branches
- Query branches sorted by distance
- Caching mechanism using Redis for faster responses
- JSON serialization for human-readable cache entries
- Automatic cache refresh upon expiration or when missing

---

## 🛠️ Tech Stack

- **Java 17+**
- **Maven**
- **Spring Boot 3+**
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
      + dto/
      + mapper/
      + service/
      + utils/
    + resources/
      + application.yml

---

## ⚙️ Caching Details



The application uses Redis as the caching provider, with each cache entry having a configurable TTL (Time-To-Live) defined in application.yml.
When inspecting Redis using the CLI, cached values are displayed in JSON:

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

After running the application, test the caching behavior using the steps below.

1️⃣ Make a GET request:
http://localhost:8080/desafio/distancia?posX=-10.0&posY=4.5

2️⃣ Open Redis CLI:
```console
docker exec -it redis redis-cli
```
3️⃣ List cached keys:
```console
keys branchesDistances*
```
4️⃣ Check stored values:
```console
get "branchesDistances::SimpleKey [-10.0, 4.5]"
```
🟡 Expected response (empty array, since no branches are registered yet):
```yaml
[ ]
```
5️⃣ Add a branch via POST request:

Body:

```yaml
{
  "branchName": "AGENCIA_1",
  "posX": -5.2,
  "posY": 3
}
```
6️⃣ Run the GET request again:
- On the first call, data is fetched from the H2 database.
- On the second call (before expiration), data is retrieved from Redis.

🟢 Expected response:
```yaml
{
  "branchesList": [
    {
      "agencia": "AGENCIA_1",
      "distancia": 5.028916384272063
    }
  ]
}
```
7️⃣ Check cache TTL (time remaining before expiration, in seconds):
```console
ttl "branchesDistances::SimpleKey [-10.0, 4.5]"
```