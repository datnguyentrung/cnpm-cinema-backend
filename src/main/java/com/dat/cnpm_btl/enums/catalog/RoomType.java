package com.dat.cnpm_btl.enums.catalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum RoomType {
    TYPE_2D("2D"),
    TYPE_3D("3D"),
    IMAX("IMAX");

    private final String code;

    RoomType(String code) {
        this.code = code;
    }

    // @JsonValue: Khi Backend trả về JSON cho FE, nó sẽ trả về code ("X") thay vì tên Enum ("PRESENT")
    @JsonValue
    public String getCode() {
        return code;
    }

    // @JsonCreator: Khi FE gửi JSON lên ("X"), Jackson dùng hàm này để tìm ra Enum (PRESENT)
    @JsonCreator
    public static RoomType fromCode(String code) {
        if (code == null) return null;
        for (RoomType status : values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid RoomType Code: " + code);
    }
}