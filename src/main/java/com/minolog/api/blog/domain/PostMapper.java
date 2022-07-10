package com.minolog.api.blog.domain;

import com.minolog.api.blog.dto.request.PostCreate;
import com.minolog.api.blog.dto.response.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "userId", source = "id")
    Post toPost(PostCreate postCreate, Long id);

    PostResponse toResponse(Post post);
}
