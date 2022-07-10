package com.minolog.api.blog.repository;

import com.minolog.api.blog.domain.Post;
import com.minolog.api.blog.dto.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch page);
}
