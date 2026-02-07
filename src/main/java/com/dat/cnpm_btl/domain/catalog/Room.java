package com.dat.cnpm_btl.domain.catalog;

import com.dat.cnpm_btl.enums.catalog.RoomType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

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
        },
        indexes = {
                @Index(name = "idx_room_cinema", columnList = "cinema_id")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    Integer roomId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "cinema_id", nullable = false)
    UUID cinemaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id", referencedColumnName = "cinema_id", insertable = false, updatable = false)
    Cinema cinema;

    @Column(name = "name", nullable = false)
    String name; // Tên phòng (VD: Phòng 01, IMAX)

    @Column(name = "total_rows", nullable = false)
    Integer totalRows; // Tổng số hàng ghế

    @Column(name = "total_cols", nullable = false)
    Integer totalCols; // Tổng số cột ghế

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    RoomType type; // Loại phòng (2D, 3D, IMAX)

}
