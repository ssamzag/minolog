package com.minolog.api.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginMember {
    private Long id;
    private String userLoginId;
    private String nickName;
    private String grade;
    private String auth;
}
