package com.example.edts.controller;

import com.example.edts.edts.EdtsApplication;
import com.example.edts.edts.restfull.dto.BookingRequest;
import com.example.edts.edts.restfull.dto.BookingResponse;
import com.example.edts.edts.restfull.dto.WebResponse;
import com.example.edts.edts.restfull.repository.BookingRepository;
import com.example.edts.edts.restfull.repository.ConcertRepository;
import com.example.edts.edts.restfull.service.BookingService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = EdtsApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingService bookingService;
    private AtomicInteger successCount = new AtomicInteger(0);

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(value = 1)
    void createBookingConcertIdNotFound() throws Exception{
        BookingRequest request = new BookingRequest();
        request.setConcertId(Long.valueOf(86));
        request.setUserId(Long.valueOf(2));
        request.setNumTicket(1);

        mockMvc.perform(
                post("/api/create/booking")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    @Order(value = 2)
    void createBookingUserIdIdNotFound() throws Exception{
        BookingRequest request = new BookingRequest();
        request.setConcertId(Long.valueOf(76));
        request.setUserId(Long.valueOf(3));
        request.setNumTicket(1);

        mockMvc.perform(
                post("/api/create/booking")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    @Order(value = 3)
    void createBookingSuccess() throws Exception{
        BookingRequest request = new BookingRequest();
        request.setConcertId(Long.valueOf(77));
        request.setUserId(Long.valueOf(2));
        request.setNumTicket(2);

        mockMvc.perform(
                post("/api/create/booking")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<BookingResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(2, response.getData().getUserId());
            assertEquals(77,response.getData().getConcertId());
            assertEquals(2,response.getData().getNumTickets());
            assertEquals(new BigDecimal("200000.00"), response.getData().getTotalPrice());
        //    assertEquals(100,response.getData().getAvailableTickets());

            assertTrue(bookingRepository.existsById(response.getData().getBookingId()));
        });

    }

    @Test
    @DirtiesContext
    @Transactional
    @Order(value = 4)
    void testRaceCondition() throws InterruptedException, ExecutionException {
        BookingRequest request = createBookingRequest(Long.valueOf(79),Long.valueOf(2) , 5);
        Callable<Void> bookingCallable = () -> {
            try {
                bookingService.create(request);
                successCount.incrementAndGet();
            } catch (Exception e) {
                e.getMessage();
            }
            return null;
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<Void>> futures = executorService.invokeAll(Arrays.asList(bookingCallable, bookingCallable));

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        assertEquals(2, successCount.get(), "Expected only 2 thread to succeed");
    }

    private BookingRequest createBookingRequest(Long concertId, Long userId, int numTickets) {
        BookingRequest request = new BookingRequest();
        request.setConcertId(concertId);
        request.setUserId(userId);
        request.setNumTicket(numTickets);
        return request;
    }

}
