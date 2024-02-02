# User API Spec

## Register User

Endpoint : POST /api/users/create

Request Body :

```json
{
  "username" : "ipeng",
  "password" : "rahasia",
  "email" : "ipeng@gmail.com" 
}
```

Response Body (Success) :

```json
{
  "data" : "OK"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Username must not blank, ???"
}
