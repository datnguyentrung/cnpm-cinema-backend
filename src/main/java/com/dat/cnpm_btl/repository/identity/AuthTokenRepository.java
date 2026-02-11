package com.dat.cnpm_btl.repository.identity;

import com.dat.cnpm_btl.domain.identity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
    Optional<AuthToken> findByUser_UserIdAndDeviceInfo(UUID userId, String idDevice);

    // Tìm token theo refreshToken VÀ phải chưa bị revoked
    Optional<AuthToken> findByRefreshTokenAndRevokedFalse(String refreshToken);

    List<AuthToken> findAllByUser_UserIdAndRevokedFalse(UUID userId);
}
