package com.dat.cnpm_btl.domain.catalog;

import com.dat.cnpm_btl.enums.catalog.CinemaStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder // Giúp tạo object dễ dàng hơn: AuthToken.builder()...build()
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cinema", schema = "catalog")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cinema_id", nullable = false)
    Integer id;

    @Column(name = "name", nullable = false, length = 255)
    String name;

    @Column(name = "address", length = 255)
    String address;

    @Column(name = "hotline", length = 20)
    String hotline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    CinemaStatus status;
}
