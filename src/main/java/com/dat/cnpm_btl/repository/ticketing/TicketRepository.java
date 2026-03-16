package com.dat.cnpm_btl.repository.ticketing;

import com.dat.cnpm_btl.domain.catalog.Seat;
import com.dat.cnpm_btl.domain.ticketing.Ticket;
import com.dat.cnpm_btl.enums.ticketing.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    @Query("""
            SELECT t.seat
            FROM Ticket t
            WHERE t.booking.showtime.id = :showtimeShowtimeId
                AND t.status IN :ticketStatus
    """)
    List<Seat> findSeatsByShowtime_ShowtimeIdAndStatusIn(String showtimeShowtimeId, List<TicketStatus> ticketStatus);

    List<Ticket> findByBooking_Id(UUID bookingId);

    List<Ticket> findByBooking_Showtime_Id(String booking_showtime_id);

    List<Ticket> findByStatus(TicketStatus status);

    Optional<Ticket> findByTicketCode(String ticketCode);

    @Query("""
            SELECT t
            FROM Ticket t
            WHERE t.booking.id = :bookingId
                AND t.status = :status
    """)
    List<Ticket> findByBooking_IdAndStatus(UUID bookingId, TicketStatus status);
}
