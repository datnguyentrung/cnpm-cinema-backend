package com.dat.cnpm_btl.dto.ticketing;

import com.dat.cnpm_btl.enums.ticketing.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class TicketDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class TicketResponse {
        String ticketId;
        String bookingId;
        String showtimeId;
        Integer seatId;
        String ticketCode;
        BigDecimal price;
        Instant createdAt;
        TicketStatus status;
        Instant checkInTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class TicketDetailResponse {
        String ticketId;
        String bookingId;
        String showtimeId;
        Integer seatId;
        String seatRow;
        Integer seatNumber;
        String ticketCode;
        BigDecimal price;
        Instant createdAt;
        TicketStatus status;
        Instant checkInTime;
        // Movie and showtime info
        String movieTitle;
        String roomName;
        Instant showtimeStart;
        Instant showtimeEnd;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class CreateTicketRequest {
        @NotNull(message = "Booking ID is required")
        String bookingId;

        @NotNull(message = "Showtime ID is required")
        String showtimeId;

        @NotNull(message = "Seat ID is required")
        Integer seatId;

        @NotNull(message = "Price is required")
        BigDecimal price;

        TicketStatus status = TicketStatus.HOLD; // Default status
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UpdateTicketStatusRequest {
        @NotNull(message = "Status is required")
        TicketStatus status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class BulkCreateTicketRequest {
        @NotNull(message = "Booking ID is required")
        String bookingId;

        @NotNull(message = "Showtime ID is required")
        String showtimeId;

        @NotNull(message = "Seat IDs are required")
        List<Integer> seatIds;

        @NotNull(message = "Price is required")
        BigDecimal price;

        TicketStatus status = TicketStatus.HOLD;
    }
}
