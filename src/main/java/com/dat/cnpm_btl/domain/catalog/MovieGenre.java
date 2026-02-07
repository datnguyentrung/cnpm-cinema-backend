package com.dat.cnpm_btl.domain.catalog;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "movie_genre",
        schema = "catalog",
        indexes = {
                @Index(name = "idx_movie_genre_movie", columnList = "movie_id"),
                @Index(name = "idx_movie_genre_genre", columnList = "genre_id")
        }
)
@IdClass(MovieGenre.MovieGenreId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieGenre {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "movie_id", nullable = false)
    UUID movieId;

    @Id
    @Column(name = "genre_id", nullable = false)
    Integer genreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id", insertable = false, updatable = false)
    Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", referencedColumnName = "genre_id", insertable = false, updatable = false)
    Genre genre;

    // Composite Primary Key Class
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class MovieGenreId implements Serializable {
        UUID movieId;
        Integer genreId;
    }

}
