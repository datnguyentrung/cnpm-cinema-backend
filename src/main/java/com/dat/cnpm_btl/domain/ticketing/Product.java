package com.dat.cnpm_btl.domain.ticketing;

import com.dat.cnpm_btl.enums.ticketing.ProductStatus;
import com.dat.cnpm_btl.enums.ticketing.ProductType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", schema = "ticketing")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    Integer id;

    @Column(name = "name")
    String name; // Tên sản phẩm (VD: Bắp phô mai, Coca lớn)

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    ProductType type; // Phân loại (FOOD, DRINK, COMBO)

    @Column(name = "price", precision = 10, scale = 2)
    BigDecimal price; // Giá bán niêm yết

    @Column(name = "description")
    String description; // Mô tả (VD: Bao gồm 1 Bắp + 2 Nước)

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    ProductStatus status; // Thay cho Boolean isActive
}
