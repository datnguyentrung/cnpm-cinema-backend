package com.dat.cnpm_btl.domain.ticketing;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "promotion", schema = "ticketing")
public class Promotion {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "promotion_id", nullable = false, length = 36)
    private UUID id;

    @Column(name = "code", unique = true, length = 50)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    @Column(name = "discount_value", precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "max_discount", precision = 10, scale = 2)
    private BigDecimal maxDiscount;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "usage_count")
    private Integer usageCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PromotionStatus status;

    @Column(name = "created_at")
    private Instant createdAt;

    public enum DiscountType {
        PERCENT,
        FIXED
    }

    public enum PromotionStatus {
        ACTIVE,
        INACTIVE
    }
}
