package com.minolog.api.member.service;

import com.minolog.api.member.domain.Member;
import com.minolog.api.member.domain.MemberRepository;
import com.minolog.api.member.dto.MemberRequest;
import com.minolog.api.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponse save(MemberRequest memberRequest) {
        Member member = memberRepository.save(Member.builder()
                .userId(memberRequest.getUserId())
                .nickName(memberRequest.getNickName())
                .password(memberRequest.getPassword())
                .build());
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"));
        return MemberResponse.of(member);
    }
}
