package com.dat.cnpm_btl.controller.ticketing;

import com.dat.cnpm_btl.dto.RestResponse;
import com.dat.cnpm_btl.dto.ticketing.TicketDTO;
import com.dat.cnpm_btl.enums.ticketing.TicketStatus;
import com.dat.cnpm_btl.service.ticketing.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    // CREATE - Single ticket
    @PostMapping
    public ResponseEntity<RestResponse<TicketDTO.TicketResponse>> createTicket(
            @Valid @RequestBody TicketDTO.CreateTicketRequest request) {
        log.info("REST: Creating ticket for booking: {}", request.getBookingId());
        TicketDTO.TicketResponse ticket = ticketService.createTicket(request);

        RestResponse<TicketDTO.TicketResponse> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Ticket created successfully");
        response.setData(ticket);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // CREATE - Bulk tickets
    @PostMapping("/bulk")
    public ResponseEntity<RestResponse<List<TicketDTO.TicketResponse>>> createTicketsBulk(
            @Valid @RequestBody TicketDTO.BulkCreateTicketRequest request) {
        log.info("REST: Creating bulk tickets for booking: {}", request.getBookingId());
        List<TicketDTO.TicketResponse> tickets = ticketService.createTicketsBulk(request);

        RestResponse<List<TicketDTO.TicketResponse>> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Tickets created successfully");
        response.setData(tickets);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // READ - Get ticket by ID
    @GetMapping("/{ticketId}")
    public ResponseEntity<RestResponse<TicketDTO.TicketResponse>> getTicketById(
            @PathVariable String ticketId) {
        log.info("REST: Getting ticket with ID: {}", ticketId);
        TicketDTO.TicketResponse ticket = ticketService.getTicketById(ticketId);

        RestResponse<TicketDTO.TicketResponse> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Ticket retrieved successfully");
        response.setData(ticket);

        return ResponseEntity.ok(response);
    }

    // READ - Get ticket by code
    @GetMapping("/code/{ticketCode}")
    public ResponseEntity<RestResponse<TicketDTO.TicketResponse>> getTicketByCode(
            @PathVariable String ticketCode) {
        log.info("REST: Getting ticket with code: {}", ticketCode);
        TicketDTO.TicketResponse ticket = ticketService.getTicketByCode(ticketCode);

        RestResponse<TicketDTO.TicketResponse> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Ticket retrieved successfully");
        response.setData(ticket);

        return ResponseEntity.ok(response);
    }

    // READ - Get tickets by booking ID
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<RestResponse<List<TicketDTO.TicketResponse>>> getTicketsByBookingId(
            @PathVariable String bookingId) {
        log.info("REST: Getting tickets for booking ID: {}", bookingId);
        List<TicketDTO.TicketResponse> tickets = ticketService.getTicketsByBookingId(bookingId);

        RestResponse<List<TicketDTO.TicketResponse>> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Tickets retrieved successfully");
        response.setData(tickets);

        return ResponseEntity.ok(response);
    }

    // READ - Get tickets by showtime ID
    @GetMapping("/showtime/{showtimeId}")
    public ResponseEntity<RestResponse<List<TicketDTO.TicketResponse>>> getTicketsByShowtimeId(
            @PathVariable String showtimeId) {
        log.info("REST: Getting tickets for showtime ID: {}", showtimeId);
        List<TicketDTO.TicketResponse> tickets = ticketService.getTicketsByShowtimeId(showtimeId);

        RestResponse<List<TicketDTO.TicketResponse>> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Tickets retrieved successfully");
        response.setData(tickets);

        return ResponseEntity.ok(response);
    }

    // READ - Get tickets by status
    @GetMapping("/status/{status}")
    public ResponseEntity<RestResponse<List<TicketDTO.TicketResponse>>> getTicketsByStatus(
            @PathVariable TicketStatus status) {
        log.info("REST: Getting tickets with status: {}", status);
        List<TicketDTO.TicketResponse> tickets = ticketService.getTicketsByStatus(status);

        RestResponse<List<TicketDTO.TicketResponse>> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Tickets retrieved successfully");
        response.setData(tickets);

        return ResponseEntity.ok(response);
    }

    // UPDATE - Change ticket status (PATCH)
    @PatchMapping("/{ticketId}/status")
    public ResponseEntity<RestResponse<TicketDTO.TicketResponse>> updateTicketStatus(
            @PathVariable String ticketId,
            @Valid @RequestBody TicketDTO.UpdateTicketStatusRequest request) {
        log.info("REST: Updating ticket {} status to {}", ticketId, request.getStatus());
        TicketDTO.TicketResponse ticket = ticketService.updateTicketStatus(ticketId, request.getStatus());

        RestResponse<TicketDTO.TicketResponse> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Ticket status updated successfully");
        response.setData(ticket);

        return ResponseEntity.ok(response);
    }

    // UPDATE - Bulk update ticket status by booking ID (PATCH)
    @PatchMapping("/booking/{bookingId}/status")
    public ResponseEntity<RestResponse<List<TicketDTO.TicketResponse>>> updateTicketStatusByBookingId(
            @PathVariable String bookingId,
            @Valid @RequestBody TicketDTO.UpdateTicketStatusRequest request) {
        log.info("REST: Updating all tickets for booking {} to status {}", bookingId, request.getStatus());
        List<TicketDTO.TicketResponse> tickets = ticketService.updateTicketStatusByBookingId(bookingId, request.getStatus());

        RestResponse<List<TicketDTO.TicketResponse>> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Tickets status updated successfully");
        response.setData(tickets);

        return ResponseEntity.ok(response);
    }

    // DELETE - Delete ticket by ID
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<RestResponse<Void>> deleteTicket(@PathVariable String ticketId) {
        log.info("REST: Deleting ticket with ID: {}", ticketId);
        ticketService.deleteTicket(ticketId);

        RestResponse<Void> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Ticket deleted successfully");

        return ResponseEntity.ok(response);
    }

    // DELETE - Delete all tickets by booking ID
    @DeleteMapping("/booking/{bookingId}")
    public ResponseEntity<RestResponse<Void>> deleteTicketsByBookingId(@PathVariable String bookingId) {
        log.info("REST: Deleting all tickets for booking ID: {}", bookingId);
        ticketService.deleteTicketsByBookingId(bookingId);

        RestResponse<Void> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Tickets deleted successfully");

        return ResponseEntity.ok(response);
    }
}
