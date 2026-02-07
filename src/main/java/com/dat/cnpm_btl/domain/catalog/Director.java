package com.dat.cnpm_btl.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "director",
        schema = "catalog",
        indexes = {
                @Index(name = "idx_director_nationality", columnList = "nationality")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id", nullable = false)
    Integer directorId;

    @Column(name = "full_name", nullable = false)
    String fullName; // Tên đạo diễn

    @Column(name = "birth_date")
    LocalDate birthDate; // Ngày sinh

    @Column(name = "nationality")
    String nationality; // Quốc tịch

}
