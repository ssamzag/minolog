package com.minolog.api.member.dto;

import com.minolog.api.common.BaseEntity;
import com.minolog.api.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class MemberResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String nickName;
    private String email;
    private String grade;
    private String auth;
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .nickName(member.getNickName())
                .userId(member.getUserId())
                .auth(member.getAuth())
                .email(member.getEmail())
                .grade(member.getGrade())
                .createdBy(member.getCreatedBy())
                .createdDate(member.getCreatedDate())
                .modifiedBy(member.getModifiedBy())
                .modifiedDate(member.getModifiedDate())
                .build();
    }
}
