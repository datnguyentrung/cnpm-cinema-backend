package com.dat.cnpm_btl.service.ticketing;

import com.dat.cnpm_btl.domain.catalog.Seat;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.enums.ticketing.TicketStatus;
import com.dat.cnpm_btl.mapper.catalog.SeatMapper;
import com.dat.cnpm_btl.repository.ticketing.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;

    private final SeatMapper seatMapper;

    public List<SeatDTO.SeatResponse> getBookedSeatsByShowtimeId(String showtimeId) {
        List<Seat> bookedSeats = ticketRepository.findSeatsByShowtime_ShowtimeIdAndStatusIn(UUID.fromString(showtimeId),
                List.of(TicketStatus.USED, TicketStatus.PAID));
        return seatMapper.toSeatResponseList(bookedSeats);
    }
}
