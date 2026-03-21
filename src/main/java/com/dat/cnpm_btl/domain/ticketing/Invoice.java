package com.dat.cnpm_btl.domain.ticketing;

import com.dat.cnpm_btl.domain.identity.Employee;
import com.dat.cnpm_btl.domain.identity.User;
import com.dat.cnpm_btl.enums.ticketing.InvoiceStatus;
import com.dat.cnpm_btl.enums.ticketing.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "invoice",
        schema = "ticketing",
        indexes = {
                @Index(name = "idx_invoice_customer", columnList = "customer_id"),
                @Index(name = "idx_invoice_booking", columnList = "booking_id")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoice {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "invoice_id", updatable = false, nullable = false, length = 36)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    Employee employee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    Booking booking;

    @Column(name = "created_at")
    Instant createdAt;

    @Column(name = "total_amount", precision = 10, scale = 2)
    BigDecimal totalAmount;

    @Column(name = "discount_amount", precision = 10, scale = 2)
    BigDecimal discountAmount;

    @Column(name = "final_amount", precision = 10, scale = 2)
    BigDecimal finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    PaymentMethod paymentMethod;

    @Column(name = "payment_transaction_id")
    String paymentTransactionId;

    @Column(name = "payment_time")
    Instant paymentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    InvoiceStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    Promotion promotion;
}
