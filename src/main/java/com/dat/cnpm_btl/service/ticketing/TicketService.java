package com.dat.cnpm_btl.service.ticketing;

import com.dat.cnpm_btl.domain.catalog.Seat;
import com.dat.cnpm_btl.domain.ticketing.Booking;
import com.dat.cnpm_btl.domain.ticketing.Ticket;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.dto.ticketing.TicketDTO;
import com.dat.cnpm_btl.enums.ticketing.TicketStatus;
import com.dat.cnpm_btl.mapper.catalog.SeatMapper;
import com.dat.cnpm_btl.mapper.ticketing.TicketMapper;
import com.dat.cnpm_btl.repository.ticketing.BookingRepository;
import com.dat.cnpm_btl.repository.ticketing.TicketRepository;
import com.dat.cnpm_btl.service.catalog.SeatService;
import com.dat.cnpm_btl.util.error.TicketAlreadyUsedException;
import com.dat.cnpm_btl.util.error.TicketNotPaidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.dat.cnpm_btl.util.TicketUtil.generateTicketCode;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {
    private final TicketRepository ticketRepository;

    private final BookingRepository bookingRepository;

    private final SeatMapper seatMapper;

    private final TicketMapper ticketMapper;

    private final SeatService seatService;

    public List<SeatDTO.SeatResponse> getBookedSeatsByShowtimeId(String showtimeId) {
        List<Seat> bookedSeats = ticketRepository.findSeatsByShowtime_ShowtimeIdAndStatusIn(showtimeId,
                List.of(TicketStatus.USED, TicketStatus.PAID));
        return seatMapper.toSeatResponseList(bookedSeats);
    }

    @Transactional
    public TicketDTO.TicketResponse createTicket(TicketDTO.CreateTicketRequest request) {
        log.info("Creating ticket for booking: {}, showtime: {}, seat: {}",
                request.getBookingId(), request.getShowtimeId(), request.getSeatId());

        Seat seat = seatService.getSeatById(request.getSeatId());

        String ticketCode = generateTicketCode(seat.getSeatType().getName());

        Booking booking = bookingRepository.findById(UUID.fromString(request.getBookingId()))
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + request.getBookingId()));

        Ticket ticket = Ticket.builder()
                .booking(booking)
                .seat(seat)
                .ticketCode(ticketCode)
                .price(request.getPrice())
                .status(request.getStatus() != null ? request.getStatus() : TicketStatus.HOLD)
                .build();

        Ticket savedTicket = ticketRepository.save(ticket);
        log.info("Created ticket with ID: {} and code: {}", savedTicket.getId(), savedTicket.getTicketCode());

        return ticketMapper.toTicketResponse(savedTicket);
    }

    // CREATE - Bulk tickets (for booking multiple seats)
    @Transactional
    public List<TicketDTO.TicketResponse> createTicketsBulk(TicketDTO.BulkCreateTicketRequest request) {
        log.info("Creating {} tickets for booking: {}", request.getSeatIds().size(), request.getBookingId());

        List<Ticket> tickets = new ArrayList<>();

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new IllegalArgumentException("Booking not found with ID: " + request.getBookingId()));

        List<Seat> seatResponses = seatService.getSeatsByIds(request.getSeatIds());

        for (Seat seat : seatResponses) {
            String ticketCode = generateTicketCode(seat.getSeatType().getName());

            Ticket ticket = Ticket.builder()
                    .booking(booking)
                    .seat(seat)
                    .ticketCode(ticketCode)
                    .price(request.getPrice())
                    .status(request.getStatus() != null ? request.getStatus() : TicketStatus.HOLD)
                    .createdAt(Instant.now())
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
        List<Ticket> tickets = ticketRepository.findByBooking_Id(UUID.fromString(bookingId));
        return ticketMapper.toTicketResponseList(tickets);
    }

    // READ - Get all tickets by showtime ID
    public List<TicketDTO.TicketResponse> getTicketsByShowtimeId(String showtimeId) {
        log.info("Fetching tickets for showtime ID: {}", showtimeId);
        List<Ticket> tickets = ticketRepository.findByBooking_Showtime_Id(showtimeId);
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
    public TicketDTO.TicketDetailResponse updateTicketStatus(String ticketCode, TicketStatus newStatus) {
        log.info("Updating ticket {} status to {}", ticketCode, newStatus);

        Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with CODE: " + ticketCode));

        TicketStatus currentStatus = ticket.getStatus();

        if (newStatus == TicketStatus.USED) {
            // Vé đã được quét rồi
            if (currentStatus == TicketStatus.USED) {
                throw new TicketAlreadyUsedException(ticketCode, ticket.getCheckInTime(),
                        ticketMapper.toTicketDetailResponse(ticket));
            }
            // Vé chưa thanh toán (HOLD) hoặc đã hủy (CANCELLED) — không được vào rạp
            if (currentStatus != TicketStatus.PAID) {
                throw new TicketNotPaidException(ticketCode, currentStatus,
                        ticketMapper.toTicketDetailResponse(ticket));
            }
        }

        ticket.setStatus(newStatus);

        // If status is USED, set check-in time
        if (newStatus == TicketStatus.USED && ticket.getCheckInTime() == null) {
            ticket.setCheckInTime(Instant.now());
            log.info("Set check-in time for ticket {}", ticketCode);
        }

        Ticket updatedTicket = ticketRepository.save(ticket);
        log.info("Ticket {} status changed from {} to {}", ticketCode, currentStatus, newStatus);

        return ticketMapper.toTicketDetailResponse(updatedTicket);
    }

    // UPDATE - Bulk update ticket status by booking ID
    @Transactional
    public List<TicketDTO.TicketResponse> updateTicketStatusByBookingId(String bookingId, TicketStatus newStatus) {
        log.info("Updating all tickets for booking {} to status {}", bookingId, newStatus);

        List<Ticket> tickets = ticketRepository.findByBooking_Id(UUID.fromString(bookingId));

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

        List<Ticket> tickets = ticketRepository.findByBooking_Id(UUID.fromString(bookingId));

        if (tickets.isEmpty()) {
            log.warn("No tickets found for booking ID: {}", bookingId);
            return;
        }

        ticketRepository.deleteAll(tickets);
        log.info("Deleted {} tickets for booking {}", tickets.size(), bookingId);
    }
}
