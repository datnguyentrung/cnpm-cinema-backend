package com.dat.cnpm_btl.domain.ticketing;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "invoice_item",
        schema = "ticketing",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_invoice_item_invoice_product", columnNames = {"invoice_id", "product_id"})
        },
        indexes = {
                @Index(name = "idx_invoice_item_invoice", columnList = "invoice_id"),
                @Index(name = "idx_invoice_item_product", columnList = "product_id")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "invoice_id", nullable = false)
    UUID invoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id", insertable = false, updatable = false)
    Invoice invoice;

    @Column(name = "product_id", nullable = false)
    Integer productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    Product product;

    @Column(name = "quantity", nullable = false)
    Integer quantity; // Số lượng

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    BigDecimal unitPrice; // Giá tại thời điểm mua

}
