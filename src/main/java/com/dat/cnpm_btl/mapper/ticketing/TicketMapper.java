package com.dat.cnpm_btl.mapper.ticketing;

import com.dat.cnpm_btl.domain.ticketing.Ticket;
import com.dat.cnpm_btl.dto.ticketing.TicketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TicketMapper {

    @Mapping(target = "ticketId", expression = "java(ticket.getTicketId() != null ? ticket.getTicketId().toString() : null)")
    @Mapping(target = "bookingId", expression = "java(ticket.getBookingId() != null ? ticket.getBookingId().toString() : null)")
    @Mapping(target = "showtimeId", expression = "java(ticket.getShowtimeId() != null ? ticket.getShowtimeId().toString() : null)")
    TicketDTO.TicketResponse toTicketResponse(Ticket ticket);

    List<TicketDTO.TicketResponse> toTicketResponseList(List<Ticket> tickets);
}
