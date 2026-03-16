package com.dat.cnpm_btl.domain.ticketing;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "promotion_condition", schema = "ticketing")
public class PromotionCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type")
    private ConditionType conditionType;

    @Column(name = "condition_value")
    private String conditionValue;

    @Column(name = "created_at")
    private Instant createdAt;

    public enum ConditionType {
        MIN_ORDER_AMOUNT,
        MEMBER_LEVEL,
        MOVIE,
        SHOWTIME
    }
}


