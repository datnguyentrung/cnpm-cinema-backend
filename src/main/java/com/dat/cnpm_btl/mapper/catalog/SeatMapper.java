package com.dat.cnpm_btl.mapper.catalog;

import com.dat.cnpm_btl.domain.catalog.Seat;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SeatMapper {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "id", target = "seatId")
    // Chỉ định dùng hàm toUpperCase cho trường này
    @Mapping(source = "seatType.name", target = "seatType", qualifiedByName = "toUpperCase")
    SeatDTO.SeatResponse toSeatResponse(Seat seat);

    // Hàm custom để MapStruct gọi đến
    @Named("toUpperCase")
    default String toUpperCase(String value) {
        if (value == null) {
            return null;
        }
        return value.toUpperCase();
    }

    List<SeatDTO.SeatResponse> toSeatResponseList(List<Seat> seats);
}
