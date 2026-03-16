package com.dat.cnpm_btl.domain.ticketing;

import com.dat.cnpm_btl.domain.catalog.Seat;
import com.dat.cnpm_btl.enums.ticketing.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
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
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_ticket_showtime_seat", columnNames = {"booking_id", "seat_id"})
        },
        indexes = {
                @Index(name = "idx_ticket_seat", columnList = "seat_id")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "ticket_id", updatable = false, nullable = false, length = 36)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    Seat seat;

    @Column(name = "ticket_code", unique = true)
    String ticketCode; // Mã vé để tạo QR

    @Column(name = "price", precision = 10, scale = 2)
    BigDecimal price; // Giá bán thực tế tại thời điểm mua

    @Column(name = "created_at")
    Instant createdAt; // Thời gian vé được phát hành

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    TicketStatus status = TicketStatus.PAID;

    @Column(name = "check_in_time")
    Instant checkInTime; // Thời gian nhân viên quét QR thành công
}
