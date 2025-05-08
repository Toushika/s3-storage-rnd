

# ☁️ S3 Storage Rnd App

This is a simple Spring Boot project to **learn how to work with AWS S3 APIs** using **LocalStack** (a tool that mocks AWS locally). You can create buckets, upload files, list files, and delete them — all without using real AWS.

---

## 📦 What is LocalStack?

**LocalStack** is a tool that allows you to run **fake AWS services** (like S3, SNS, SQS, etc.) on your local machine.
This helps you **test your cloud code without paying AWS** or needing internet access.
---

## ✅ What This Project Does

This app provides REST APIs to:

| Endpoint         | Method | Description                          |
| ---------------- | ------ | ------------------------------------ |
| `/create/bucket` | POST   | Create a new S3 bucket               |
| `/get/bucket`    | GET    | List all existing S3 buckets         |
| `/delete/bucket` | DELETE | Delete an S3 bucket                  |
| `/add/file`      | POST   | Upload a file to a bucket            |
| `/view/files`    | POST   | View all files in a given bucket     |
| `/delete/file`   | DELETE | Delete a specific file from a bucket |

---

## ⚙️ How to Run

### 1. Clone the project

```bash
git clone https://your-repo-url
cd s3-storage-rnd
```

### 2. Start LocalStack using Docker

Use this `docker-compose.yml` file:

```yaml
version: '3.8'

services:
  localstack:
    image: localstack/localstack:latest
    container_name: localstack
    ports:
      - "4566:4566"
    environment:
      - SERVICES=s3
      - DEBUG=1
      - DATA_DIR=/var/lib/localstack
    volumes:
      - "./localstack:/var/lib/localstack"
      - "/var/run/docker.sock:/var/run/docker.sock"
```

Then run:

```bash
docker-compose up
```

### 3. Configure your `application.yml`

```yaml
aws:
  region: us-east-1
  s3:
    endpoint: http://localhost:4566
```

### 4. Start Spring Boot App

```bash
./gradlew bootRun
```

---

## 🛠 Example cURL Commands

### Create a bucket:
```bash
curl --location 'http://localhost:8091/create/bucket' \
--header 'Content-Type: application/json' \
--data '{
    "bucketName":"myfiles"
}'
```

### View Bucket lists:
```bash
curl --location 'http://localhost:8091/get/bucket'
```

### Delete a bucket:
```bash
curl --location --request DELETE 'http://localhost:8091/delete/bucket' \
--header 'Content-Type: application/json' \
--data ' {
    "bucketName":"myfiles"
}'
```

### Upload a file:

```bash
curl --location 'http://localhost:8091/add/file' \
--form 'file=@"/Users/toushikaislam/Documents/FututreMock.png"' \
--form 'bucketName="myphotos"'
```

### View files in a bucket:

```bash
curl --location 'http://localhost:8091/view/files' \
--header 'Content-Type: application/json' \
--data '{
    "bucketName": "myphotos"
}'
```

### Delete a File:

```bash
curl --location --request DELETE 'http://localhost:8091/delete/file' \
--header 'Content-Type: application/json' \
--data '{
    "bucketName": "myphotos",
    "fileName": "FututreMock.png"
}'
```
---

## 📝 Notes
* This project uses `software.amazon.awssdk:s3` SDK
* All API responses are returned as simple JSON objects

---

