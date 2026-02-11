package com.dat.cnpm_btl.enums.ticketing;

public enum TicketStatus {
    HOLD, // Vé đang giữ chỗ, chưa thanh toán
    PAID, // Vé đã được thanh toán
    USED, // Vé đã được sử dụng để vào rạp
    CANCELLED // Vé đã bị hủy
}
