package com.dat.cnpm_btl.mapper.catalog;

import com.dat.cnpm_btl.domain.catalog.Seat;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SeatMapper {

    @Mapping(source = "type", target = "seatType")
    SeatDTO.SeatResponse toSeatResponse(Seat seat);

    List<SeatDTO.SeatResponse> toSeatResponseList(List<Seat> seats);
}
