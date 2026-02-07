package com.dat.cnpm_btl.domain.catalog;

import com.dat.cnpm_btl.enums.catalog.SeatType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "seat",
        schema = "catalog",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_seat_room_row_number", columnNames = {"room_id", "row_label", "seat_number"}),
                @UniqueConstraint(name = "uk_seat_room_grid", columnNames = {"room_id", "grid_row", "grid_col"})
        },
        indexes = {
                @Index(name = "idx_seat_room", columnList = "room_id")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false)
    Integer seatId;

    @Column(name = "room_id", nullable = false)
    Integer roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", insertable = false, updatable = false)
    Room room;

    @Column(name = "row_label", nullable = false)
    String rowLabel; // Nhãn hàng (A, B, C...)

    @Column(name = "seat_number", nullable = false)
    Integer seatNumber; // Số ghế theo hàng (1, 2, 3...)

    @Column(name = "grid_row", nullable = false)
    Integer gridRow; // Tọa độ X trên ma trận

    @Column(name = "grid_col", nullable = false)
    Integer gridCol; // Tọa độ Y trên ma trận

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    SeatType type; // Loại ghế (STANDARD, VIP, COUPLE)

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    Boolean isActive = true; // Ghế có sử dụng được không

}
