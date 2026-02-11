package com.dat.cnpm_btl.mapper.catalog;

import com.dat.cnpm_btl.domain.catalog.Movie;
import com.dat.cnpm_btl.dto.catalog.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MovieMapper {

    @Mapping(source = "movieId", target = "movieId", qualifiedByName = "uuidToString")
    MovieDTO.MovieResponse toMovieResponse(Movie movie);

    List<MovieDTO.MovieResponse> toMovieResponseList(List<Movie> movies);

    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }
}
