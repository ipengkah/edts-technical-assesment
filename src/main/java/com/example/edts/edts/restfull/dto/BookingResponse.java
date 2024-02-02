package com.example.edts.edts.restfull.dto;

import com.example.edts.edts.restfull.model.Concert;
import com.example.edts.edts.restfull.model.User;
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
public class BookingResponse {

    private Long bookingId;
    private Long userId;
    private Long concertId;
    private int numTickets;
    private BigDecimal totalPrice;
    private LocalDateTime bookingDateTime;

}
