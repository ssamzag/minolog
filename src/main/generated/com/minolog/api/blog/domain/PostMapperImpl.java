package com.minolog.api.blog.domain;

import com.minolog.api.blog.dto.request.PostCreate;
import com.minolog.api.blog.dto.response.PostResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-10T11:49:33+0900",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 11.0.15 (Azul Systems, Inc.)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post toPost(PostCreate postCreate, Long id) {
        if ( postCreate == null && id == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        if ( postCreate != null ) {
            post.title( postCreate.getTitle() );
            post.content( postCreate.getContent() );
        }
        post.userId( id );

        return post.build();
    }

    @Override
    public PostResponse toResponse(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse.PostResponseBuilder postResponse = PostResponse.builder();

        postResponse.id( post.getId() );
        postResponse.title( post.getTitle() );
        postResponse.content( post.getContent() );

        return postResponse.build();
    }
}
