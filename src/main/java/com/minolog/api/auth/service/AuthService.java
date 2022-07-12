package com.minolog.api.auth.service;

import com.minolog.api.auth.domain.LoginMember;
import com.minolog.api.auth.dto.TokenRequest;
import com.minolog.api.auth.dto.TokenResponse;
import com.minolog.api.auth.instracture.JwtTokenProvider;
import com.minolog.api.member.domain.Member;
import com.minolog.api.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public TokenResponse login(TokenRequest tokenRequest) {
        Member member = memberRepository.findByUserId(tokenRequest.getUserId()).orElseThrow(AuthorizationException::new);
        if (member == null) {
            throw new AuthorizationException("아이디가 존재하지 않습니다.");
        }
        member.checkPassword(tokenRequest.getPassword());
        String token = jwtTokenProvider.createToken(tokenRequest.getUserId());
        return new TokenResponse(token);
    }

    public LoginMember findMemberByToken(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            return new LoginMember();
        }
        String userId = jwtTokenProvider.getPayload(credentials);
        Member member = memberRepository.findByUserId(userId).orElseThrow(RuntimeException::new);
        return new LoginMember(member.getId(), member.getUserId(), member.getNickName(), member.getGrade(), member.getAuth());
    }

    @ExceptionHandler
    public ResponseEntity authorizationExceptionHandler(AuthorizationException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
