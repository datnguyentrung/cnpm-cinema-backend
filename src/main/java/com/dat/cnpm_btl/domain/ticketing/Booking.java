package com.dat.cnpm_btl.domain.ticketing;

import com.dat.cnpm_btl.domain.identity.User;
import com.dat.cnpm_btl.enums.ticketing.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
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
                @Index(name = "idx_booking_showtime", columnList = "showtime_id")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "booking_id", updatable = false, nullable = false, length = 36)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;

    @Column(name = "user_id")
    String user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", insertable = false, updatable = false)
    Showtime showtime;

    @Column(name = "showtime_id")
    String showtime_id;

    @Column(name = "created_at")
    Instant createdAt; // Thời gian đặt

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    BookingStatus status = BookingStatus.PENDING;

    @Column(name = "expired_at")
    Instant expiredAt; // Thời gian customer được giữ ghế trước khi thanh toán (5-10p)
}
