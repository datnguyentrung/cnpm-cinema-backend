package com.dat.cnpm_btl.service.ticketing;

import com.dat.cnpm_btl.domain.ticketing.Showtime;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.dto.ticketing.ShowtimeDTO;
import com.dat.cnpm_btl.mapper.ticketing.ShowtimeMapper;
import com.dat.cnpm_btl.repository.ticketing.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShowtimeService {
    private final ShowtimeRepository showtimeRepository;

    private final ShowtimeMapper showtimeMapper;

    private final TicketService ticketService;

    public Showtime getShowtimeById(String showtimeId) {
        return showtimeRepository.findById(UUID.fromString(showtimeId))
                .orElseThrow(() -> new RuntimeException("Showtime not found with id: " + showtimeId));
    }

    public ShowtimeDTO.ShowTimeWithSeatsResponse getShowtimeWithSeats(String showtimeId) {
        Showtime showtimeResponse = getShowtimeById(showtimeId);

        List<SeatDTO.SeatResponse> selectedSeats = ticketService.getBookedSeatsByShowtimeId(showtimeId);

        return showtimeMapper.toShowtimeWithSeatsResponse(showtimeResponse, selectedSeats);
    }
}
