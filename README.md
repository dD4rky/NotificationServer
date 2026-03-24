# Centralized Notification server

## 1. Installation
### docker

```bash
git clone https://github.com/dD4rky/NotificationServer.git
```

```docker-compose
services:
  server:
    build: .
    ports:
      - "8080:8080"
    environment:
      - DATASOURCE_URL=jdbc:postgresql://{host}:{port}/{database_name}
      - DATASOURCE_USERNAME=...
      - DATASOURCE_PASSWORD=...
      - TELEGRAM_TOKEN=...
      - TELEGRAM_ACCOUNT_ID=...
```

## 2. Usage

### Sending messages
```http request
POST "https://{host}:{port}/notification/send"
Content-Type: application/json

{
    "initiator":"dd4rky",
    "message":"example"
}
```
Response
```http response
HTTP/1.1 201 
Location: https://{host}:{port}/notification/get_status?uuid=f10e9614-19a0-4c87-b1bf-45f49208b400
Content-Type: text/plain;charset=UTF-8
Content-Length: 36
Date: Tue, 24 Mar 2026 19:27:35 GMT

"f10e9614-19a0-4c87-b1bf-45f49208b400"
```
### Getting status of notification
```http request
GET "https://{host}:{port}/notification/get_status?uuid=f10e9614-19a0-4c87-b1bf-45f49208b400"
```
Response
```http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 24 Mar 2026 19:26:17 GMT

{
  "notificationStatus": "IN_PROCESS",
  "responseStatus": 200
}
```

# 3. Work in progress
* JWT security

# 4. Future plans
* Other methods of delivery messages
  * Long polling requests
  * probably something else
