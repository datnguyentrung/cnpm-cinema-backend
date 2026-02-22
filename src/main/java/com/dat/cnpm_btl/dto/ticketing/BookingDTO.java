package com.dat.cnpm_btl.dto.ticketing;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class BookingDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookSeatsRequest {
        @NotNull(message = "Showtime ID is required")
        private String showtimeId;

        @NotEmpty(message = "Seat IDs list cannot be empty")
        private List<Integer> seatIds;
    }
}
