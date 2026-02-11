package com.dat.cnpm_btl.mapper.ticketing;

import com.dat.cnpm_btl.domain.ticketing.Showtime;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.dto.ticketing.ShowtimeDTO;
import com.dat.cnpm_btl.mapper.catalog.MovieMapper;
import com.dat.cnpm_btl.mapper.catalog.RoomMapper;
import com.dat.cnpm_btl.mapper.catalog.SeatMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {MovieMapper.class, RoomMapper.class, SeatMapper.class}
)
public interface ShowtimeMapper {

    @Mapping(source = "basePrice", target = "ticketPrice")
    @Mapping(source = "movie", target = "movieResponse")
    @Mapping(source = "room", target = "roomResponse")
    ShowtimeDTO.ShowtimeResponse toShowtimeResponse(Showtime showtime);

    List<ShowtimeDTO.ShowtimeResponse> toShowtimeResponseList(List<Showtime> showtimes);

    @Mapping(source = "showtime.showtimeId", target = "showtimeId")
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
}
