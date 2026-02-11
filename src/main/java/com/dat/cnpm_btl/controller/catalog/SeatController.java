package com.dat.cnpm_btl.controller.catalog;

import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.service.catalog.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seats")
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<SeatDTO.SeatResponse>> getSeatsByRoomId(@PathVariable Integer roomId) {
        log.info("Getting seats for room: {}", roomId);
        List<SeatDTO.SeatResponse> seats = seatService.getSeatsByRoomId(roomId);
        return ResponseEntity.ok(seats);
    }
}
