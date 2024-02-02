package com.example.edts.edts.restfull.service;

import com.example.edts.edts.restfull.dto.ConcertRequest;
import com.example.edts.edts.restfull.dto.ConcertResponse;
import com.example.edts.edts.restfull.dto.SearchConcertRequest;
import com.example.edts.edts.restfull.model.Concert;
import com.example.edts.edts.restfull.repository.ConcertRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ConcertService {
    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ValidationService validationService;

    public ConcertResponse create(ConcertRequest request) {
        validationService.validate(request);

        Concert concert = new Concert();
        concert.setArtistName(request.getArtistName());
        concert.setVenue(request.getVenue());
        concert.setDateTime(request.getDateTime());
        concert.setTicketPrice(request.getTicketPrice());
        concert.setAvailableTickets(request.getAvailableTickets());

        concertRepository.save(concert);

        return toConcertResponse(concert);

    }

    private ConcertResponse toConcertResponse(Concert concert) {
        return ConcertResponse.builder()
                .concertId(concert.getConcertId())
                .artistName(concert.getArtistName())
                .venue(concert.getVenue())
                .dateTime(concert.getDateTime())
                .ticketPrice(concert.getTicketPrice())
                .availableTickets(concert.getAvailableTickets())
                .build();
    }
    @Transactional(readOnly = true)
    public Page<ConcertResponse> search(SearchConcertRequest request) {
        Specification<Concert> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(request.getArtistName())) {
                predicates.add(builder.or(
                        builder.like(root.get("artistName"), "%" + request.getArtistName() + "%")));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Concert> concerts = concertRepository.findAll(specification, pageable);
        List<ConcertResponse> contactResponses = concerts.getContent().stream()
                .map(this::toConcertResponse)
                .toList();

        return new PageImpl<>(contactResponses, pageable, concerts.getTotalElements());
    }
}
