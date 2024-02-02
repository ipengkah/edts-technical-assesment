package com.example.edts.controller;

import com.example.edts.edts.EdtsApplication;
import com.example.edts.edts.restfull.dto.ConcertRequest;
import com.example.edts.edts.restfull.dto.ConcertResponse;
import com.example.edts.edts.restfull.dto.WebResponse;
import com.example.edts.edts.restfull.model.Concert;
import com.example.edts.edts.restfull.repository.ConcertRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = EdtsApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class ConcertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {concertRepository.deleteAll();}

    @Test
    @Order(value = 2)
    void concertCreateSuccess() throws Exception {
        ConcertRequest request = new ConcertRequest();
        request.setArtistName("Dewa 19");
        request.setVenue("Jakarta International Stadium");
        request.setDateTime(LocalDateTime.of(2024, Month.JULY, 29, 19, 30, 00));
        request.setTicketPrice(new BigDecimal(100000));
        request.setAvailableTickets(100);

        mockMvc.perform(
                post("/api/concert/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ConcertResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("Dewa 19", response.getData().getArtistName());
            assertEquals("Jakarta International Stadium", response.getData().getVenue());
            assertEquals(LocalDateTime.of(2024, Month.JULY, 29, 19, 30, 00),response.getData().getDateTime());
            assertEquals(new BigDecimal(100000), response.getData().getTicketPrice());
            assertEquals(100,response.getData().getAvailableTickets());

            assertTrue(concertRepository.existsById(response.getData().getConcertId()));
        });
    }

    @Test
    @Order(value = 1)
    void createConcertBadRequest() throws Exception{
        ConcertRequest request = new ConcertRequest();
        request.setArtistName("");
        request.setVenue("");

        mockMvc.perform(
                post("/api/concert/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    @Order(value = 3)
    void searchConcert() throws Exception {

        for (int i = 1; i < 11; i++) {
            Concert concert = new Concert();
            concert.setArtistName("Dewa " + i);
            concert.setVenue("Jakarta International Stadium" + i);
            concert.setDateTime(LocalDateTime.of(2024, Month.JULY, + i, 19, 30, 00));
            concert.setTicketPrice(new BigDecimal(100000));
            concert.setAvailableTickets(100);
            concertRepository.save(concert);
        }

        mockMvc.perform(
                get("/api/concert/search")
                        .queryParam("artisname", "Dewa 10")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ConcertResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals(10, response.getData().size());
            assertEquals(1, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });
    }
}
