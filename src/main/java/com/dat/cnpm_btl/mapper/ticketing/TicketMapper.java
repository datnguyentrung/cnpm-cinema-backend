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

    @Mapping(target = "ticketId", source = "ticketId")
    @Mapping(target = "bookingId", source = "bookingId")
    @Mapping(target = "showtimeId", source = "showtimeId")
    TicketDTO.TicketResponse toTicketResponse(Ticket ticket);

    @Mapping(target = "seatRow", source = "seat.rowLabel")
    @Mapping(target = "seatNumber", source = "ticket.seat.seatNumber")
    @Mapping(target = "movieTitle", source = "ticket.showtime.movie.title")
    @Mapping(target = "roomName", source = "ticket.showtime.room.name")
    @Mapping(target = "showtimeStart", source = "ticket.showtime.startTime")
    @Mapping(target = "showtimeEnd", source = "ticket.showtime.endTime")
    TicketDTO.TicketDetailResponse toTicketDetailResponse(Ticket ticket);

    List<TicketDTO.TicketResponse> toTicketResponseList(List<Ticket> tickets);
}
