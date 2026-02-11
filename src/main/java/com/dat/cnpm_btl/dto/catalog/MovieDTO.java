package com.dat.cnpm_btl.dto.catalog;

import com.dat.cnpm_btl.enums.catalog.AgeRating;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

public class MovieDTO {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class MovieResponse {
        String movieId;
        String title; // Tên phim
        Integer durationMinutes; // Thời lượng (phút)
        String posterUrl; // Link ảnh poster
        String description; // Tóm tắt nội dung
        LocalDate releaseDate;
        AgeRating ageRating; // Giới hạn tuổi (P, C13, C16, C18)
    }
}
