package com.dat.cnpm_btl.domain.identity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // <--- BẮT BUỘC PHẢI CÓ ANNOTATION NÀY
@Table(name = "membership", schema = "identity") // Cập nhật đúng schema
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", nullable = false)
    MembershipRank rank;

    @Column(name = "join_date", nullable = false)
    LocalDate joinDate;

    @Column(name = "current_loyalty_points")
    Integer currentLoyaltyPoints;

    @Column(name = "membership_status", nullable = false)
    String membershipStatus; // Hoặc Enum
}