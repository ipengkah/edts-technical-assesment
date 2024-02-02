package com.example.edts.edts.restfull.service;

import com.example.edts.edts.restfull.dto.BookingRequest;
import com.example.edts.edts.restfull.dto.BookingResponse;
import com.example.edts.edts.restfull.model.Booking;
import com.example.edts.edts.restfull.model.Concert;
import com.example.edts.edts.restfull.repository.BookingRepository;
import com.example.edts.edts.restfull.repository.ConcertRepository;
import com.example.edts.edts.restfull.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ConcertRepository concertRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ValidationService validationService;
    @Transactional
    public BookingResponse create(BookingRequest request) {
        validationService.validate(request);

        Concert concert = concertRepository.findById(request.getConcertId())
                .orElseThrow(() -> new ResponseStatusException
                        (HttpStatus.NOT_FOUND, "Concert Id Not Found"));

        entityManager.lock(concert, LockModeType.PESSIMISTIC_WRITE);

        if (concert.getAvailableTickets() >= request.getNumTicket()){
            concert.setAvailableTickets(concert.getAvailableTickets() - request.getNumTicket());

            Booking booking = new Booking();
            booking.setUser(userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new ResponseStatusException
                            (HttpStatus.NOT_FOUND, "User Id Not Found")));
            booking.setConcert(concert);
            booking.setNumTickets(request.getNumTicket());
            booking.setTotalPrice(concert.getTicketPrice().multiply(BigDecimal.valueOf(request.getNumTicket())));
            booking.setBookingDateTime(LocalDateTime.now());

            concertRepository.save(concert);
            bookingRepository.save(booking);

            return toBookingResponse(booking);

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not Enough Ticket");
        }

    }

    private BookingResponse toBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .userId(booking.getUser().getUserId())
                .concertId(booking.getConcert().getConcertId())
                .numTickets(booking.getNumTickets())
                .totalPrice(booking.getTotalPrice())
                .bookingDateTime(booking.getBookingDateTime())
                .build();
    }
}
