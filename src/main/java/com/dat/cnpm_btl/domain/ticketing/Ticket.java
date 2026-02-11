package com.dat.cnpm_btl.domain.ticketing;

import com.dat.cnpm_btl.domain.catalog.Seat;
import com.dat.cnpm_btl.enums.ticketing.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
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
        name = "ticket",
        schema = "ticketing",
        indexes = {
                @Index(name = "idx_ticket_booking", columnList = "booking_id"),
                @Index(name = "idx_ticket_showtime", columnList = "showtime_id"),
                @Index(name = "idx_ticket_seat", columnList = "seat_id"),
                @Index(name = "idx_ticket_code", columnList = "ticket_code"),
                @Index(name = "idx_ticket_status", columnList = "status")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "ticket_id", updatable = false, nullable = false)
    UUID ticketId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "booking_id", nullable = false)
    UUID bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id", insertable = false, updatable = false)
    Booking booking;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "showtime_id", nullable = false)
    UUID showtimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", referencedColumnName = "showtime_id", insertable = false, updatable = false)
    Showtime showtime;

    @Column(name = "seat_id", nullable = false)
    Integer seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", referencedColumnName = "seat_id", insertable = false, updatable = false)
    Seat seat;

    @Column(name = "ticket_code", nullable = false, unique = true)
    String ticketCode; // Mã vé để tạo QR

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    BigDecimal price; // Giá bán thực tế tại thời điểm mua

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt; // Thời gian vé được phát hành

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    TicketStatus status = TicketStatus.PAID;

    @Column(name = "check_in_time")
    Instant checkInTime; // Thời gian nhân viên quét QR thành công

}
