package com.minolog.api.blog.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostSearch {

    private static final int MIN_SIZE = 1000;
    private static final int DEFAULT_PAGE = 1;
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 20;

    public long getOffset() {
        return (long) (Math.max(page, DEFAULT_PAGE) - 1) * Math.min(size, MIN_SIZE);
    }

}
