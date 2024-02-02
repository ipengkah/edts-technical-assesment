# Booking API Spec

## Create booking

Endpoint : POST /api/create/booking

Request Body :

```json
{
  "concertId" : "77",
  "userId" : "2",
  "numTicket" : 1
}
```

Response Body (Success) :

```json
{
  "data": {
    "bookingId": 3,
    "userId": 2,
    "concertId": 77,
    "numTickets": 1,
    "totalPrice": 100000.00,
    "bookingDateTime": "2024-02-02T10:25:13.5316187"
  },
  "errors": null,
  "paging": null
}
```

Response Body (Failed) :

```json
{
  "errors" : "concertId/userId not found"
}
```
