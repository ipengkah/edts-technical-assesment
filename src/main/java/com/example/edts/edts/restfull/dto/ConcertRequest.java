package com.example.edts.edts.restfull.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcertRequest {

    @NotBlank
    @Size(max = 100)
    private String artistName;

    @NotBlank
    @Size(max = 1000)
    @NotBlank
    private String venue;
    
    private LocalDateTime dateTime;

    private BigDecimal ticketPrice;

    private int availableTickets;
}
