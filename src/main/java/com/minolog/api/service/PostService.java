package com.minolog.api.service;

import com.minolog.api.domain.Post;
import com.minolog.api.domain.PostEditor;
import com.minolog.api.dto.request.PostCreate;
import com.minolog.api.dto.request.PostEdit;
import com.minolog.api.dto.request.PostSearch;
import com.minolog.api.dto.response.PostResponse;
import com.minolog.api.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OrderBy;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    public com.minolog.api.domain.Post write(PostCreate postCreate) {
        com.minolog.api.domain.Post post = com.minolog.api.domain.Post.builder().
                title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        return postRepository.save(post);

    }

    public PostResponse get(Long id) {
        com.minolog.api.domain.Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 없"));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch)
                .stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();
        PostEditor postEditor = editorBuilder
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.update(postEditor);
    }


}
