package com.dat.cnpm_btl.domain.ticketing;

import com.dat.cnpm_btl.domain.catalog.Movie;
import com.dat.cnpm_btl.domain.catalog.Room;
import com.dat.cnpm_btl.enums.ticketing.ShowtimeStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

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
        },
        indexes = {
                @Index(name = "idx_showtime_movie", columnList = "movie_id"),
                @Index(name = "idx_showtime_room", columnList = "room_id"),
                @Index(name = "idx_showtime_start", columnList = "start_time"),
                @Index(name = "idx_showtime_status", columnList = "status")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowTime {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "showtime_id", updatable = false, nullable = false)
    UUID showtimeId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "movie_id", nullable = false)
    UUID movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id", insertable = false, updatable = false)
    Movie movie;

    @Column(name = "room_id", nullable = false)
    Integer roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false)
    Room room;

    @Column(name = "start_time", nullable = false)
    Instant startTime; // Giờ bắt đầu

    @Column(name = "end_time", nullable = false)
    Instant endTime; // Giờ kết thúc + dọn dẹp

    @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
    BigDecimal basePrice; // Giá vé cơ bản của suất này

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    ShowtimeStatus status = ShowtimeStatus.OPENING;

}
