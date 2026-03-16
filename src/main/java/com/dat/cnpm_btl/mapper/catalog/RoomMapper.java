package com.dat.cnpm_btl.mapper.catalog;

import com.dat.cnpm_btl.domain.catalog.Room;
import com.dat.cnpm_btl.dto.catalog.RoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoomMapper {

    @Mapping(source = "id", target = "roomId")
    @Mapping(source = "name", target = "roomName")
    @Mapping(source = "totalRows", target = "totalRows")
    @Mapping(source = "totalCols", target = "totalCols")
    @Mapping(source = "type", target = "type")
    RoomDTO.RoomResponse toRoomResponse(Room room);

    List<RoomDTO.RoomResponse> toRoomResponseList(List<Room> rooms);
}
