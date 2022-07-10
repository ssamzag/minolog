package com.minolog.api.blog.repository;

import com.minolog.api.blog.domain.Post;
import com.minolog.api.blog.dto.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.minolog.api.blog.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(10)
                .offset(postSearch.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
