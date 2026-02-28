package com.dat.cnpm_btl.util.error;

import com.dat.cnpm_btl.dto.ticketing.TicketDTO;
import lombok.Getter;

import java.time.Instant;

@Getter
public class TicketAlreadyUsedException extends RuntimeException {
    private final Instant checkInTime;
    private final TicketDTO.TicketDetailResponse ticketDetail;

    public TicketAlreadyUsedException(String ticketCode, Instant checkInTime, TicketDTO.TicketDetailResponse ticketDetail) {
        super("Ticket [" + ticketCode + "] was already used at " + checkInTime);
        this.checkInTime = checkInTime;
        this.ticketDetail = ticketDetail;
    }

}
