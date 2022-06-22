package com.minolog.api.repository;

import com.minolog.api.domain.Post;
import com.minolog.api.domain.QPost;
import com.minolog.api.dto.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.minolog.api.domain.QPost.post;

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
