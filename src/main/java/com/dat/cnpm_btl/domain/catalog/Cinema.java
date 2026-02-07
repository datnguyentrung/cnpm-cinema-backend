package com.dat.cnpm_btl.domain.catalog;

import com.dat.cnpm_btl.domain.identity.Employee;
import com.dat.cnpm_btl.enums.catalog.CinemaStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Builder // Giúp tạo object dễ dàng hơn: AuthToken.builder()...build()
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "cinema", // Tên bảng số nhiều, snake_case
        schema = "catalog",
        indexes = {
                @Index(name = "idx_cinema_manager", columnList = "manager_id"), // Index để tìm kiếm theo manager
                @Index(name = "idx_cinema_status", columnList = "status") // Index để tìm kiếm theo status
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cinema {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "cinema_id", updatable = false, nullable = false)
    UUID cinemaId;

    @Column(name = "name", nullable = false)
    String name; // Tên rạp (VD: CGV Aeon Hà Đông)

    @Column(name = "address", columnDefinition = "TEXT")
    String address; // Địa chỉ hiển thị

    @Column(name = "hotline")
    String hotline;

    // Quản lý rạp (Optional) - Khóa ngoại → employee(user_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "user_id")
    Employee manager;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    CinemaStatus status = CinemaStatus.OPEN;

}
