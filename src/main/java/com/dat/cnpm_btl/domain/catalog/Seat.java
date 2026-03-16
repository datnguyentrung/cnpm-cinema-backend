package com.dat.cnpm_btl.domain.catalog;

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
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id", nullable = false)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    Room room;

    @Column(name = "row_label", length = 10)
    String rowLabel; // Nhãn hàng (A, B, C...)

    @Column(name = "seat_number")
    Integer seatNumber; // Số ghế theo hàng (1, 2, 3...)

    @Column(name = "grid_row")
    Integer gridRow; // Tọa độ X trên ma trận

    @Column(name = "grid_col")
    Integer gridCol; // Tọa độ Y trên ma trận

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_type_id", nullable = false)
    SeatType seatType;

    @Column(name = "is_active")
    @Builder.Default
    Boolean isActive = true; // Ghế có sử dụng được không

}
