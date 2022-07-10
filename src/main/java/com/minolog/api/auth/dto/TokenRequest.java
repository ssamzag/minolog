package com.minolog.api.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequest {
    private String userId;
    private String password;

    @Builder
    public TokenRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
