package com.example.edts.edts.restfull.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertResponse {
    private Long concertId;
    private String artistName;
    private String venue;
    private LocalDateTime dateTime;
    private BigDecimal ticketPrice;
    private int availableTickets;
}
