package com.example.edts.edts.restfull.controller;

import com.example.edts.edts.restfull.dto.*;
import com.example.edts.edts.restfull.model.Concert;
import com.example.edts.edts.restfull.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConcertController {

    @Autowired
    private ConcertService concertService;

    @PostMapping(
            path ="api/concert/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ConcertResponse> create (Concert concert, @RequestBody ConcertRequest request) {
        ConcertResponse concertResponse = concertService.create(request);
        return WebResponse.<ConcertResponse>builder().data(concertResponse).build();
    }

    @GetMapping(
            path = "/api/concert/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ConcertResponse>> search(
                                                     @RequestParam(value = "artistname", required = false) String artistName,
                                                     @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        SearchConcertRequest request = SearchConcertRequest.builder()
                .artistName(artistName)
                .page(page)
                .size(size)
                .build();

        Page<ConcertResponse> contactResponses = concertService.search(request);
        return WebResponse.<List<ConcertResponse>>builder()
                .data(contactResponses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(contactResponses.getNumber())
                        .totalPage(contactResponses.getTotalPages())
                        .size(contactResponses.getSize())
                        .build())
                .build();
    }

}
