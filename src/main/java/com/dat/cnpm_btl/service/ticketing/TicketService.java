package com.dat.cnpm_btl.service.ticketing;

import com.dat.cnpm_btl.domain.catalog.Seat;
import com.dat.cnpm_btl.domain.ticketing.Ticket;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.dto.ticketing.TicketDTO;
import com.dat.cnpm_btl.enums.ticketing.TicketStatus;
import com.dat.cnpm_btl.mapper.catalog.SeatMapper;
import com.dat.cnpm_btl.mapper.ticketing.TicketMapper;
import com.dat.cnpm_btl.repository.ticketing.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;

    private final SeatMapper seatMapper;

    private final TicketMapper ticketMapper;

    public List<SeatDTO.SeatResponse> getBookedSeatsByShowtimeId(String showtimeId) {
        List<Seat> bookedSeats = ticketRepository.findSeatsByShowtime_ShowtimeIdAndStatusIn(UUID.fromString(showtimeId),
                List.of(TicketStatus.USED, TicketStatus.PAID));
        return seatMapper.toSeatResponseList(bookedSeats);
    }

    // CREATE - Single ticket
    @Transactional
    public TicketDTO.TicketResponse createTicket(TicketDTO.CreateTicketRequest request) {
        log.info("Creating ticket for booking: {}, showtime: {}, seat: {}",
                request.getBookingId(), request.getShowtimeId(), request.getSeatId());

        String ticketCode = generateTicketCode();

        Ticket ticket = Ticket.builder()
                .bookingId(UUID.fromString(request.getBookingId()))
                .showtimeId(UUID.fromString(request.getShowtimeId()))
                .seatId(request.getSeatId())
                .ticketCode(ticketCode)
                .price(request.getPrice())
                .status(request.getStatus() != null ? request.getStatus() : TicketStatus.HOLD)
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);
        log.info("Created ticket with ID: {} and code: {}", savedTicket.getTicketId(), savedTicket.getTicketCode());

        return ticketMapper.toTicketResponse(savedTicket);
    }

    // CREATE - Bulk tickets (for booking multiple seats)
    @Transactional
    public List<TicketDTO.TicketResponse> createTicketsBulk(TicketDTO.BulkCreateTicketRequest request) {
        log.info("Creating {} tickets for booking: {}", request.getSeatIds().size(), request.getBookingId());

        List<Ticket> tickets = new ArrayList<>();
        UUID bookingId = UUID.fromString(request.getBookingId());
        UUID showtimeId = UUID.fromString(request.getShowtimeId());

        for (Integer seatId : request.getSeatIds()) {
            String ticketCode = generateTicketCode();

            Ticket ticket = Ticket.builder()
                    .bookingId(bookingId)
                    .showtimeId(showtimeId)
                    .seatId(seatId)
                    .ticketCode(ticketCode)
                    .price(request.getPrice())
                    .status(request.getStatus() != null ? request.getStatus() : TicketStatus.HOLD)
                    .build();

            tickets.add(ticket);
        }

        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);
        log.info("Successfully created {} tickets", savedTickets.size());

        return ticketMapper.toTicketResponseList(savedTickets);
    }

    // READ - Get ticket by ID
    public TicketDTO.TicketResponse getTicketById(String ticketId) {
        log.info("Fetching ticket with ID: {}", ticketId);
        Ticket ticket = ticketRepository.findById(UUID.fromString(ticketId))
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + ticketId));
        return ticketMapper.toTicketResponse(ticket);
    }

    // READ - Get ticket by code
    public TicketDTO.TicketResponse getTicketByCode(String ticketCode) {
        log.info("Fetching ticket with code: {}", ticketCode);
        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with code: " + ticketCode));
        return ticketMapper.toTicketResponse(ticket);
    }

    // READ - Get all tickets by booking ID
    public List<TicketDTO.TicketResponse> getTicketsByBookingId(String bookingId) {
        log.info("Fetching tickets for booking ID: {}", bookingId);
        List<Ticket> tickets = ticketRepository.findByBookingId(UUID.fromString(bookingId));
        return ticketMapper.toTicketResponseList(tickets);
    }

    // READ - Get all tickets by showtime ID
    public List<TicketDTO.TicketResponse> getTicketsByShowtimeId(String showtimeId) {
        log.info("Fetching tickets for showtime ID: {}", showtimeId);
        List<Ticket> tickets = ticketRepository.findByShowtimeId(UUID.fromString(showtimeId));
        return ticketMapper.toTicketResponseList(tickets);
    }

    // READ - Get all tickets by status
    public List<TicketDTO.TicketResponse> getTicketsByStatus(TicketStatus status) {
        log.info("Fetching tickets with status: {}", status);
        List<Ticket> tickets = ticketRepository.findByStatus(status);
        return ticketMapper.toTicketResponseList(tickets);
    }

    // UPDATE - Change ticket status (PATCH)
    @Transactional
    public TicketDTO.TicketResponse updateTicketStatus(String ticketId, TicketStatus newStatus) {
        log.info("Updating ticket {} status to {}", ticketId, newStatus);

        Ticket ticket = ticketRepository.findById(UUID.fromString(ticketId))
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + ticketId));

        TicketStatus oldStatus = ticket.getStatus();
        ticket.setStatus(newStatus);

        // If status is USED, set check-in time
        if (newStatus == TicketStatus.USED && ticket.getCheckInTime() == null) {
            ticket.setCheckInTime(Instant.now());
            log.info("Set check-in time for ticket {}", ticketId);
        }

        Ticket updatedTicket = ticketRepository.save(ticket);
        log.info("Ticket {} status changed from {} to {}", ticketId, oldStatus, newStatus);

        return ticketMapper.toTicketResponse(updatedTicket);
    }

    // UPDATE - Bulk update ticket status by booking ID
    @Transactional
    public List<TicketDTO.TicketResponse> updateTicketStatusByBookingId(String bookingId, TicketStatus newStatus) {
        log.info("Updating all tickets for booking {} to status {}", bookingId, newStatus);

        List<Ticket> tickets = ticketRepository.findByBookingId(UUID.fromString(bookingId));

        if (tickets.isEmpty()) {
            throw new IllegalArgumentException("No tickets found for booking ID: " + bookingId);
        }

        tickets.forEach(ticket -> {
            ticket.setStatus(newStatus);
            if (newStatus == TicketStatus.USED && ticket.getCheckInTime() == null) {
                ticket.setCheckInTime(Instant.now());
            }
        });

        List<Ticket> updatedTickets = ticketRepository.saveAll(tickets);
        log.info("Updated {} tickets for booking {}", updatedTickets.size(), bookingId);

        return ticketMapper.toTicketResponseList(updatedTickets);
    }

    // DELETE - Delete ticket by ID
    @Transactional
    public void deleteTicket(String ticketId) {
        log.info("Deleting ticket with ID: {}", ticketId);

        if (!ticketRepository.existsById(UUID.fromString(ticketId))) {
            throw new IllegalArgumentException("Ticket not found with ID: " + ticketId);
        }

        ticketRepository.deleteById(UUID.fromString(ticketId));
        log.info("Deleted ticket with ID: {}", ticketId);
    }

    // DELETE - Delete all tickets by booking ID
    @Transactional
    public void deleteTicketsByBookingId(String bookingId) {
        log.info("Deleting all tickets for booking ID: {}", bookingId);

        List<Ticket> tickets = ticketRepository.findByBookingId(UUID.fromString(bookingId));

        if (tickets.isEmpty()) {
            log.warn("No tickets found for booking ID: {}", bookingId);
            return;
        }

        ticketRepository.deleteAll(tickets);
        log.info("Deleted {} tickets for booking {}", tickets.size(), bookingId);
    }

    // UTILITY - Generate unique ticket code
    private String generateTicketCode() {
        // Format: TKT-TIMESTAMP-RANDOM
        // Example: TKT-20240222150530-A1B2
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(5);
        String randomPart = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return String.format("TKT-%s-%s", timestamp, randomPart);
    }
}
