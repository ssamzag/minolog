package com.minolog.api.auth.service;

import com.minolog.api.auth.domain.LoginMember;
import com.minolog.api.auth.dto.TokenRequest;
import com.minolog.api.auth.dto.TokenResponse;
import com.minolog.api.auth.instracture.JwtTokenProvider;
import com.minolog.api.member.domain.Member;
import com.minolog.api.member.domain.MemberRepository;
import com.minolog.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public TokenResponse login(TokenRequest tokenRequest) {
        Member member = memberRepository.findByUserId(tokenRequest.getUserId())
                .orElseThrow(() -> new AuthorizationException("아이디를 확인해 주세요."));

        member.checkPassword(tokenRequest.getPassword());
        String token = jwtTokenProvider.createToken(tokenRequest.getUserId());
        return new TokenResponse(token);
    }

    public LoginMember findMemberByToken(String credentials) {
        if (!jwtTokenProvider.validateToken(credentials)) {
            throw new AuthorizationException("다시 로그인해 주세요.");
        }
        String userId = jwtTokenProvider.getPayload(credentials);
        Member member = memberService.findMemberByUserId(userId);
        return new LoginMember(member.getId(), member.getUserId(), member.getNickName(), member.getGrade(), member.getAuth());
    }
}
