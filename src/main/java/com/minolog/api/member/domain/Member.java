package com.minolog.api.member.domain;

import com.minolog.api.auth.service.AuthorizationException;
import com.minolog.api.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", unique = true)
    private String userId;

    @NotBlank(message = "닉네임을 입력해 주세요")
    private String nickName;

    private String password;

    private String email;

    private String grade;

    private String auth;

    public void checkPassword(String password) {
        if (!this.password.equals(password)) {
            throw new AuthorizationException("비밀번호가 틀림");
        }
    }
}
