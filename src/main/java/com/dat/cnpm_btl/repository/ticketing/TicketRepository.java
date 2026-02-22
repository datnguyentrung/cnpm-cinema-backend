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
            WHERE t.showtime.showtimeId = :showtimeShowtimeId
                AND t.status IN :ticketStatus
    """)
    List<Seat> findSeatsByShowtime_ShowtimeIdAndStatusIn(UUID showtimeShowtimeId, List<TicketStatus> ticketStatus);

    List<Ticket> findByBookingId(UUID bookingId);

    List<Ticket> findByShowtimeId(UUID showtimeId);

    List<Ticket> findByStatus(TicketStatus status);

    Optional<Ticket> findByTicketCode(String ticketCode);

    @Query("""
            SELECT t
            FROM Ticket t
            WHERE t.bookingId = :bookingId
                AND t.status = :status
    """)
    List<Ticket> findByBookingIdAndStatus(UUID bookingId, TicketStatus status);
}
