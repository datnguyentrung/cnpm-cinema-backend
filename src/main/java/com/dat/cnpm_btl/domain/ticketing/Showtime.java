package com.dat.cnpm_btl.domain.ticketing;

import com.dat.cnpm_btl.domain.catalog.Movie;
import com.dat.cnpm_btl.domain.catalog.Room;
import com.dat.cnpm_btl.enums.ticketing.ShowtimeStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "showtime",
        schema = "ticketing",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_showtime_room_start", columnNames = {"room_id", "start_time"})
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Showtime {

    @Id
    @Column(name = "showtime_id", updatable = false, nullable = false, length = 36)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    Room room;

    @Column(name = "start_time")
    Instant startTime; // Giờ bắt đầu

    @Column(name = "end_time")
    Instant endTime; // Giờ kết thúc + dọn dẹp

    @Column(name = "base_price", precision = 10, scale = 2)
    BigDecimal basePrice; // Giá vé cơ bản của suất này

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    ShowtimeStatus status = ShowtimeStatus.OPENING;
}
