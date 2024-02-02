# Concert API Spec

## Create Concert

Endpoint : POST /api/concert/create

Request Body :

```json
{
  "artistName" : "Dewa 9",
  "venue" : "Jakarta International Stadium4",
  "dateTime" : "2024-07-04 19:30:00" ,
  "ticketPrice":100000,
  "availableTicket":100
}
```

Response Body (Success) :

```json
{
  "concertId": 55,
  "artistName" : "Dewa 9",
  "venue" : "Jakarta International Stadium4",
  "dateTime" : "2024-07-04 19:30:00" ,
  "ticketPrice":100000,
  "availableTicket":100
}
```

Response Body (Failed) :

```json
{
  "errors" : "Artist name must not blank, ???"
}
```
## Search Concert

Endpoint : GET /api/concert/search

Query Param :

- artistname : String, artistname, using like query, optional
- page : Integer, start from 0, default 0
- size : Integer, default 10

Response Body (Success) :

```json

{
"data": [
{
  "concertId": 55,
  "artistName": "Dewa 10",
  "venue": "Jakarta International Stadium10",
  "dateTime": "2024-07-10T19:30:00",
  "ticketPrice": 100000.00,
  "availableTickets": 100
}
],
"errors": null,
"paging": {
"currentPage": 0,
"totalPage": 1,
"size": 10
}
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized"
}
```
