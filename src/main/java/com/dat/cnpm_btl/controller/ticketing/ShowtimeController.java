package com.dat.cnpm_btl.controller.ticketing;

import com.dat.cnpm_btl.dto.ticketing.ShowtimeDTO;
import com.dat.cnpm_btl.service.ticketing.ShowtimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/showtimes")
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @GetMapping("/{showtimeId}")
    public ResponseEntity<ShowtimeDTO.ShowTimeWithSeatsResponse> getShowtimeWithSeats(@PathVariable String showtimeId) {
        log.info("Getting showtime with seats for showtimeId: {}", showtimeId);
        ShowtimeDTO.ShowTimeWithSeatsResponse response = showtimeService.getShowtimeWithSeats(showtimeId);
        return ResponseEntity.ok(response);
    }
}
