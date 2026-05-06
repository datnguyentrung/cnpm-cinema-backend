package com.dat.cnpm_btl.domain.identity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // <--- BẮT BUỘC PHẢI CÓ ANNOTATION NÀY
@Table(name = "membership_rank", schema = "identity") // Cập nhật đúng schema
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MembershipRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    Integer id;

    @Column(name = "rank_name", unique = true, nullable = false)
    String rankName;

    @Column(name = "minimum_points", nullable = false)
    Integer minimumPoints;

    @Column(name = "benefits_description")
    String benefitsDescription;

    @Column(name = "status")
    String status; // Hoặc Enum
}