package com.dat.cnpm_btl.domain.identity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder // Sử dụng SuperBuilder thay vì Builder để hỗ trợ kế thừa
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "employee", // Tên bảng số nhiều, snake_case
        schema = "identity",
        indexes = {
                @Index(name = "idx_employee_cinema", columnList = "cinema_id") // Index để tìm kiếm theo cinema
        }
)
@PrimaryKeyJoinColumn(name = "user_id")
@EqualsAndHashCode(callSuper = true) // So sánh object dựa trên cả field của User
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee extends User {

    // Nhân viên thuộc rạp nào (Liên kết schema catalog)
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "cinema_id")
    UUID cinemaId;

    // Ngày bắt đầu làm việc
    @Column(name = "hire_date")
    LocalDate hireDate;

}
