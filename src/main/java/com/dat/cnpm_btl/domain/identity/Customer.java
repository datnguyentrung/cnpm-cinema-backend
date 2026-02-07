package com.dat.cnpm_btl.domain.identity;

import com.dat.cnpm_btl.enums.identity.Member;
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
@Table(
        name = "customer", // Tên bảng số nhiều, snake_case
        schema = "identity",
        indexes = {
//                @Index(name = "idx_refresh_token", columnList = "refresh_token") // Index để tìm kiếm nhanh
        }
)
@PrimaryKeyJoinColumn(name = "user_id")
@EqualsAndHashCode(callSuper = true) // So sánh object dựa trên cả field của User
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends User {

    @Column(name = "registration_date")
    Instant registrationDate;

    // MAPPING QUAN HỆ VỚI MEMBER
    // Giả sử: Một Employee liên kết với một hồ sơ Member (Hội viên) cụ thể.
    // Nếu logic là 1 Employee quản lý nhiều Member, bạn cần List<Member> và @OneToMany.
    // Ở đây tôi đang để @OneToOne (hoặc @ManyToOne) dựa trên biến đơn 'member'.

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type")
    Member member;
}
