package com.example.edts.edts.restfull.controller;

import com.example.edts.edts.restfull.dto.*;
import com.example.edts.edts.restfull.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping(
            path ="api/create/booking",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<BookingResponse> create (@RequestBody BookingRequest request) {
        BookingResponse bookingResponse = bookingService.create(request);
        return WebResponse.<BookingResponse>builder().data(bookingResponse).build();
    }

}
