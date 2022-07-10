package com.minolog.api.blog.service;

import com.minolog.api.blog.domain.PostMapper;
import com.minolog.api.blog.dto.response.PostResponse;
import com.minolog.api.blog.domain.Post;
import com.minolog.api.blog.dto.request.PostCreate;
import com.minolog.api.blog.dto.request.PostEdit;
import com.minolog.api.blog.dto.request.PostSearch;
import com.minolog.api.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Post write(PostCreate postCreate, Long id) {
        Post post = postMapper.toPost(postCreate, id);
        return postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = findPostByIdOrElseThrow(id);
        return postMapper.toResponse(post);
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch)
                .stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long userId, Long id, PostEdit postEdit) {
        Post post = findPostByIdOrElseThrow(id);

        if (!Objects.equals(post.getUserId(), userId)) {
            throw new IllegalArgumentException("본인 글만 삭제 가능");
        }

        post.update(postEdit);
    }

    private Post findPostByIdOrElseThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
    }


}
