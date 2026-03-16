package com.dat.cnpm_btl.domain.identity;

import com.dat.cnpm_btl.enums.identity.MemberLevel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@SuperBuilder // Sử dụng SuperBuilder thay vì Builder để hỗ trợ kế thừa
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer", schema = "identity")
@PrimaryKeyJoinColumn(name = "user_id")
@EqualsAndHashCode(callSuper = true) // So sánh object dựa trên cả field của User
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends User {

    @Column(name = "registration_date")
    Instant registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_level")
    MemberLevel memberLevel;

    @Column(name = "loyalty_points")
    Integer loyaltyPoints;
}
