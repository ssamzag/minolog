package com.minolog.api.blog.dto.response;

import com.minolog.api.blog.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;

    public PostResponse(Post post) {
        this(post.getId(), post.getTitle(), post.getContent());
    }
}
