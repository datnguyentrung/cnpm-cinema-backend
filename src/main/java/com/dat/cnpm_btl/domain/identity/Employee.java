package com.dat.cnpm_btl.domain.identity;

import com.dat.cnpm_btl.domain.catalog.Cinema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder // Sử dụng SuperBuilder thay vì Builder để hỗ trợ kế thừa
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee", schema = "identity")
@PrimaryKeyJoinColumn(name = "user_id")
@EqualsAndHashCode(callSuper = true) // So sánh object dựa trên cả field của User
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id")
    Cinema cinema;

    @Column(name = "hire_date")
    LocalDate hireDate;

    @Column(name = "position", length = 100)
    String position;

    @Column(name = "salary", precision = 10, scale = 2)
    BigDecimal salary;
}
