package com.dat.cnpm_btl.mapper.ticketing;

import com.dat.cnpm_btl.domain.ticketing.Showtime;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.dto.ticketing.ShowtimeDTO;
import com.dat.cnpm_btl.mapper.catalog.MovieMapper;
import com.dat.cnpm_btl.mapper.catalog.RoomMapper;
import com.dat.cnpm_btl.mapper.catalog.SeatMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.Duration;
import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MovieMapper.class, RoomMapper.class, SeatMapper.class}
)
public interface ShowtimeMapper {

    @Mapping(source = "id", target = "showtimeId")
    @Mapping(source = "basePrice", target = "ticketPrice")
    @Mapping(source = "movie", target = "movieResponse")
    @Mapping(source = "room", target = "roomResponse")
    ShowtimeDTO.ShowtimeResponse toShowtimeResponse(Showtime showtime);

    @AfterMapping
    default void afterToShowtimeResponse(Showtime showtime, @MappingTarget ShowtimeDTO.ShowtimeResponse showtimeResponse) {
        if (showtime.getStartTime() != null && showtime.getEndTime() != null) {
            long duration = Duration.between(showtime.getStartTime(), showtime.getEndTime()).toMinutes();
            showtimeResponse.getMovieResponse().setDurationMinutes((int) duration);
        }
    }

    List<ShowtimeDTO.ShowtimeResponse> toShowtimeResponseList(List<Showtime> showtimes);

    @Mapping(source = "showtime.id", target = "showtimeId")
    @Mapping(source = "showtime.movie", target = "movieResponse")
    @Mapping(source = "showtime.room", target = "roomResponse")
    @Mapping(source = "showtime.startTime", target = "startTime")
    @Mapping(source = "showtime.endTime", target = "endTime")
    @Mapping(source = "showtime.basePrice", target = "ticketPrice")
    @Mapping(source = "showtime.status", target = "status")
    @Mapping(source = "selectedSeats", target = "selectedSeats")
    ShowtimeDTO.ShowTimeWithSeatsResponse toShowtimeWithSeatsResponse(
            Showtime showtime,
            List<SeatDTO.SeatResponse> selectedSeats
    );

    @Named("getDurationInMinutes")
    default long getDurationInMinutes(Showtime showtime) {
        if (showtime.getStartTime() == null || showtime.getEndTime() == null) {
            return 0;
        }
        return Duration.between(showtime.getStartTime(), showtime.getEndTime()).toMinutes();
    }
}
