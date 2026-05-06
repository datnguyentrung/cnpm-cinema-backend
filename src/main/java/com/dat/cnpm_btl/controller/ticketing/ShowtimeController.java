package com.dat.cnpm_btl.controller.ticketing;

import com.dat.cnpm_btl.dto.ticketing.ShowtimeDTO;
import com.dat.cnpm_btl.service.ticketing.ShowtimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/showtimes")
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @GetMapping("/{showtimeId}")
    public ResponseEntity<ShowtimeDTO.ShowTimeWithSeatsResponse> getShowtimeWithSeats(@PathVariable UUID showtimeId) {
        log.info("Getting showtime with seats for showtimeId: {}", showtimeId);
        ShowtimeDTO.ShowTimeWithSeatsResponse response = showtimeService.getShowtimeWithSeats(showtimeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ShowtimeDTO.ShowtimeResponse>> getShowTimeResponseByFilter(
            @RequestParam LocalDate date) {
        log.info("Getting showtimes for date: {}", date);
        List<ShowtimeDTO.ShowtimeResponse> response = showtimeService.getShowTimeResponseByDate(date);
        return ResponseEntity.ok(response);
    }
}
