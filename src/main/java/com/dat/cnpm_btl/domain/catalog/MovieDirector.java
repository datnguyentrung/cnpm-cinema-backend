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
        name = "movie_director",
        schema = "catalog",
        indexes = {
                @Index(name = "idx_movie_director_movie", columnList = "movie_id"),
                @Index(name = "idx_movie_director_director", columnList = "director_id")
        }
)
@IdClass(MovieDirector.MovieDirectorId.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieDirector {

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "movie_id", nullable = false)
    UUID movieId;

    @Id
    @Column(name = "director_id", nullable = false)
    Integer directorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "movie_id", insertable = false, updatable = false)
    Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id", referencedColumnName = "director_id", insertable = false, updatable = false)
    Director director;

    // Composite Primary Key Class
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class MovieDirectorId implements Serializable {
        UUID movieId;
        Integer directorId;
    }

}
