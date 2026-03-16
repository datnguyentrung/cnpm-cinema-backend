package com.dat.cnpm_btl.domain.catalog;

import com.dat.cnpm_btl.enums.catalog.RoomType;
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
        name = "room",
        schema = "catalog",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_room_cinema_name", columnNames = {"cinema_id", "name"})
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cinema_id", nullable = false)
    Cinema cinema;

    @Column(name = "name", nullable = false, length = 100)
    String name; // Tên phòng (VD: Phòng 01, IMAX)

    @Column(name = "total_rows")
    Integer totalRows; // Tổng số hàng ghế

    @Column(name = "total_cols")
    Integer totalCols; // Tổng số cột ghế

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    RoomType type; // Loại phòng (2D, 3D, IMAX)
}
