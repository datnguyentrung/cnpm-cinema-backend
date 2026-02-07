package com.dat.cnpm_btl.domain.ticketing;

import com.dat.cnpm_btl.domain.identity.Customer;
import com.dat.cnpm_btl.domain.identity.Employee;
import com.dat.cnpm_btl.enums.ticketing.InvoiceStatus;
import com.dat.cnpm_btl.enums.ticketing.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
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
                @Index(name = "idx_invoice_employee", columnList = "employee_id"),
                @Index(name = "idx_invoice_booking", columnList = "booking_id"),
                @Index(name = "idx_invoice_status", columnList = "status"),
                @Index(name = "idx_invoice_created", columnList = "created_at")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoice {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "invoice_id", updatable = false, nullable = false)
    UUID invoiceId;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "customer_id", nullable = false)
    UUID customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    Customer customer;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "employee_id")
    UUID employeeId; // Nullable - nếu khách mua Online thì NULL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    Employee employee;

    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "booking_id")
    UUID bookingId; // Liên kết với đơn đặt vé

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id", insertable = false, updatable = false)
    Booking booking;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt; // Thời gian thanh toán

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    BigDecimal totalAmount; // Tổng tiền cuối cùng = Vé + Bắp nước

    @Column(name = "final_amount", nullable = false, precision = 12, scale = 2)
    BigDecimal finalAmount; // Tổng tiền thực thu (Sau khi trừ khuyến mãi)

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    PaymentMethod paymentMethod; // Phương thức (CASH, MOMO, VNPAY, BANK_TRANSFER, CARD)

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    InvoiceStatus status = InvoiceStatus.PENDING;

}
