package com.dat.cnpm_btl.controller.ticketing;

import com.dat.cnpm_btl.dto.RestResponse;
import com.dat.cnpm_btl.dto.ticketing.BookingDTO;
import com.dat.cnpm_btl.dto.ticketing.TicketDTO;
import com.dat.cnpm_btl.service.ticketing.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<List<TicketDTO.TicketResponse>> createBooking(
            @Valid @RequestBody BookingDTO.BookSeatsRequest request) {

        log.info("REST: Booking {} seats for showtime: {}", request.getSeatIds().size(), request.getShowtimeId());

        List<TicketDTO.TicketResponse> tickets = bookingService.bookSeats(
                request.getShowtimeId(),
                request.getSeatIds()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(tickets);
    }

}
