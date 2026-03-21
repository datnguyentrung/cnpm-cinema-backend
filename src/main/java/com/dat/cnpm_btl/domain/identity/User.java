package com.dat.cnpm_btl.domain.identity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder // Thay cho @Builder để hỗ trợ kế thừa
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
// Quan trọng: Định nghĩa chiến lược kế thừa.
// JOINED: Tạo 2 bảng riêng biệt, bảng con trỏ ID về bảng cha.
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user", schema = "identity") // Tên bảng nên viết thường (lowercase)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "user_id", updatable = false, nullable = false, length = 36)
    UUID id;

    @Column(name = "full_name", nullable = false)
    String fullName;

    // Mật khẩu hash, không được null
    @Column(name = "password_hash", nullable = false)
    String passwordHash;

    // Số điện thoại là định danh đăng nhập -> Unique và Not Null
    @Column(name = "phone_number", nullable = false, unique = true, length = 20)
    String phoneNumber;

    // Email có thể null (nếu người dùng chưa cập nhật) nhưng nếu có thì phải Unique
    @Column(name = "email", unique = true)
    String email;

    @Column(name = "birth_date")
    LocalDate birthDate;

    // Trạng thái kích hoạt, mặc định là true khi tạo mới (tùy logic của bạn)
    @Column(name = "is_active")
    @Builder.Default
    Boolean isActive = true;

    // --- QUAN HỆ VỚI BẢNG ROLE ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false) // Khóa ngoại liên kết
    Role role;

    // --- AUDITING FIELDS (Tự động) ---

    @Column(name = "created_at")
    Instant createdAt;

    @Column(name = "updated_at")
    Instant updatedAt;

}
