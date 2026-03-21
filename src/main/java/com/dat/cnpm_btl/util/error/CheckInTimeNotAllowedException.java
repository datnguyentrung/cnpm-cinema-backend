package com.dat.cnpm_btl.util.error;

import com.dat.cnpm_btl.dto.ticketing.TicketDTO;
import lombok.Getter;

import java.time.Instant;

@Getter
public class CheckInTimeNotAllowedException extends RuntimeException {
    private final Instant showtimeStart;
    private final TicketDTO.TicketDetailResponse ticketDetail;

    public CheckInTimeNotAllowedException(String ticketCode, Instant showtimeStart, TicketDTO.TicketDetailResponse ticketDetail) {
        super("Cannot check-in ticket [" + ticketCode + "] before 15 minutes of showtime start at " + showtimeStart);
        this.showtimeStart = showtimeStart;
        this.ticketDetail = ticketDetail;
    }
}

