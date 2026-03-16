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

    @Mapping(target = "ticketId", source = "id")
    @Mapping(target = "bookingId", source = "ticket.booking.id")
    @Mapping(target = "seatId", source = "ticket.seat.id")
    @Mapping(target = "showtimeId", source = "ticket.booking.showtime.id")
    TicketDTO.TicketResponse toTicketResponse(Ticket ticket);

    @Mapping(target = "seatRow", source = "seat.rowLabel")
    @Mapping(target = "seatNumber", source = "ticket.seat.seatNumber")
    @Mapping(target = "movieTitle", source = "ticket.booking.showtime.movie.title")
    @Mapping(target = "roomName", source = "ticket.booking.showtime.room.name")
    @Mapping(target = "showtimeStart", source = "ticket.booking.showtime.startTime")
    @Mapping(target = "showtimeEnd", source = "ticket.booking.showtime.endTime")
    TicketDTO.TicketDetailResponse toTicketDetailResponse(Ticket ticket);

    List<TicketDTO.TicketResponse> toTicketResponseList(List<Ticket> tickets);
}
