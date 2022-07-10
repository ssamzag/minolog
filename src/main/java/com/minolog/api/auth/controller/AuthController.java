package com.minolog.api.auth.controller;

import com.minolog.api.auth.dto.TokenRequest;
import com.minolog.api.auth.dto.TokenResponse;
import com.minolog.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest tokenRequest) {
        TokenResponse response = authService.login(tokenRequest);
        return ResponseEntity.ok().body(response);
    }
}
