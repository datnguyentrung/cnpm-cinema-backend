package com.dat.cnpm_btl.dto.catalog;

import lombok.*;
import lombok.experimental.FieldDefaults;

public class SeatDTO {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class SeatResponse {
        Integer seatId;
        Integer roomId;
        String rowLabel; // Nhãn hàng (A, B, C...)
        Integer seatNumber; // Số ghế theo hàng (1, 2, 3...)
        Integer gridRow; // Tọa độ X trên ma trận
        Integer gridCol; // Tọa độ Y trên ma trận
        String seatType; // Loại ghế (STANDARD, VIP, COUPLE)
        Boolean isActive; // Ghế có sử dụng được không
    }
}
