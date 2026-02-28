package com.dat.cnpm_btl.util.error;

import com.dat.cnpm_btl.dto.ticketing.TicketDTO;
import com.dat.cnpm_btl.enums.ticketing.TicketStatus;
import lombok.Getter;

@Getter
public class TicketNotPaidException extends RuntimeException {
    private final TicketStatus currentStatus;
    private final TicketDTO.TicketDetailResponse ticketDetail;

    public TicketNotPaidException(String ticketCode, TicketStatus currentStatus, TicketDTO.TicketDetailResponse ticketDetail) {
        super("Ticket [" + ticketCode + "] cannot be used — current status is " + currentStatus);
        this.currentStatus = currentStatus;
        this.ticketDetail = ticketDetail;
    }
}
