package com.dat.cnpm_btl.dto.ticketing;

import com.dat.cnpm_btl.dto.catalog.MovieDTO;
import com.dat.cnpm_btl.dto.catalog.RoomDTO;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.enums.ticketing.ShowtimeStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class ShowtimeDTO {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ShowtimeResponse {
        String showtimeId;

        MovieDTO.MovieResponse movieResponse;

        RoomDTO.RoomResponse roomResponse;

        Instant startTime; // ISO 8601 format
        Instant endTime;   // ISO 8601 format

        BigDecimal ticketPrice;
        ShowtimeStatus status;    // e.g., "SCHEDULED", "ONGOING", "COMPLETED", "CANCELLED"
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class ShowTimeWithSeatsResponse extends ShowtimeResponse {
        List<SeatDTO.SeatResponse> selectedSeats; // Danh sách ghế đã được chọn cho suất chiếu này
    }
}
