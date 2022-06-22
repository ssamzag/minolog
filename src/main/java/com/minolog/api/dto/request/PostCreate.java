package com.minolog.api.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class PostCreate {
    @NotBlank(message = "타이틀이 비었음")
    private String title;
    @NotBlank(message = "내용을 입력하라")
    private String content;

    @Builder
    private PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
