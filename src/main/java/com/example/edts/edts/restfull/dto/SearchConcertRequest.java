package com.example.edts.edts.restfull.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchConcertRequest {
    private String artistName;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;
}
