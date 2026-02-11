package com.dat.cnpm_btl.service.identity;

import com.dat.cnpm_btl.domain.identity.Role;
import com.dat.cnpm_btl.domain.identity.User;
import com.dat.cnpm_btl.dto.Security.ChangePasswordReq;
import com.dat.cnpm_btl.repository.identity.UserRepository;
import com.dat.cnpm_btl.util.error.InvalidPasswordException;
import com.dat.cnpm_btl.util.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Value("${password.time_password_change_days}")
    private long timePasswordChange;

    // Hàm dùng chung để gán thông tin User
    public <T extends User> void setupBaseUser(T user, String roleCode) {
        Role role = roleService.getRoleById(roleCode);

        user.setRole(role);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setIsActive(true);
    }

    // Lấy user theo idUser
    public User getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found with idUser: " + userId));
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with idUser: " + userId));
    }
    public User getUserByPhoneNumber(String phoneNumber) throws UserNotFoundException {
        return (User) userRepository.findByPhoneNumber (phoneNumber)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));
    }

    public void changePassword(String phoneNumber, ChangePasswordReq passwordReq) {
        User user = getUserByPhoneNumber(phoneNumber);

        if (!passwordEncoder.matches(passwordReq.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Mật khẩu cũ không đúng");
        }

        if (!passwordReq.getNewPassword().equals(passwordReq.getConfirmPassword())) {
            throw new InvalidPasswordException("Xác nhận mật khẩu không khớp");
        }

        user.setPassword(passwordEncoder.encode(passwordReq.getNewPassword()));
        userRepository.save(user);
    }
}
