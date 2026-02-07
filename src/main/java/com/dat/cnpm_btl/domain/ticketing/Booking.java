package com.dat.cnpm_btl.domain.ticketing;

import com.dat.cnpm_btl.domain.identity.User;
import com.dat.cnpm_btl.enums.ticketing.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "booking",
        schema = "ticketing",
        indexes = {
                @Index(name = "idx_booking_user", columnList = "user_id"),
                @Index(name = "idx_booking_showtime", columnList = "showtime_id"),
                @Index(name = "idx_booking_status", columnList = "status"),
                @Index(name = "idx_booking_created", columnList = "created_at")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "booking_id", updatable = false, nullable = false)
    UUID bookingId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "user_id", nullable = false)
    UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    User user;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "showtime_id", nullable = false)
    UUID showtimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", referencedColumnName = "showtime_id", insertable = false, updatable = false)
    ShowTime showtime;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt; // Thời gian đặt

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    BookingStatus status = BookingStatus.PENDING;

    @Column(name = "expired_at")
    Instant expiredAt; // Thời gian customer được giữ ghế trước khi thanh toán (5-10p)

}
