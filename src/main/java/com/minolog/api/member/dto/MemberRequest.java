package com.minolog.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberRequest {
    private String userId;
    private String nickName;
    private String password;
}
