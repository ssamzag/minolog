package com.minolog.api.blog.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class PostEdit {
    @NotBlank(message = "타이틀이 비었음")
    private String title;
    @NotBlank(message = "내용을 입력하라")
    private String content;

    @Builder
    private PostEdit(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
