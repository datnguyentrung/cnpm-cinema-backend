package com.dat.cnpm_btl.dto.ticketing;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
public class BookingDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookSeatsRequest {
        @NotNull(message = "Showtime ID is required")
        private UUID showtimeId;

        @NotEmpty(message = "Seat IDs list cannot be empty")
        private List<Integer> seatIds;
    }
}
