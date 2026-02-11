package com.dat.cnpm_btl.dto.catalog;

import com.dat.cnpm_btl.enums.catalog.RoomType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

public class RoomDTO {
    @Data
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class RoomResponse {
        Integer roomId;
        String roomName; // Tên phòng chiếu
        Integer totalRows; // Tổng số hàng ghế
        Integer totalCols; // Tổng số cột ghế
        RoomType type; // Loại phòng (2D, 3D, IMAX)
    }
}
