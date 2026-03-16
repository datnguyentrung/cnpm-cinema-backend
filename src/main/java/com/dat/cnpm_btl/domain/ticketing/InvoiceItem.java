package com.dat.cnpm_btl.domain.ticketing;

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
@Table(
        name = "invoice_item",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_invoice_item_invoice_product", columnNames = {"invoice_id", "product_id"})
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(name = "quantity")
    Integer quantity; // Số lượng

    @Column(name = "unit_price", precision = 10, scale = 2)
    BigDecimal unitPrice; // Giá tại thời điểm mua

}
