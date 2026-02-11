package com.dat.cnpm_btl.dto.Security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
public class LoginRes {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String idDevice;
    private UserLogin user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLogin {
        private UUID userId;
        private String role;
        private String startDate;
        private Boolean isActive;

        @Override
        public String toString() {
            return "UserLogin{id= " + userId + ", role= " + role + "}";
        }
    }
}
