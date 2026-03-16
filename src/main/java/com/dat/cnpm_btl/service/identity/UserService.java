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
}
