package com.dat.cnpm_btl.domain.catalog;

import com.dat.cnpm_btl.enums.catalog.AgeRating;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie", schema = "catalog")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "movie_id", updatable = false, nullable = false, length = 36)
    UUID id;

    @Column(name = "title", nullable = false)
    String title; // Tên phim

    @Column(name = "director")
    String director;

    @Column(name = "duration", nullable = false)
    Integer duration; // Thời lượng (phút)

    @Column(name = "poster_url")
    String posterUrl; // Link ảnh poster

    @Column(name = "description")
    String description; // Tóm tắt nội dung

    @Column(name = "release_date")
    LocalDate releaseDate; // Ngày công chiếu

    @Enumerated(EnumType.STRING)
    @Column(name = "age_rating")
    AgeRating ageRating; // Giới hạn tuổi (P, C13, C16, C18)
}
