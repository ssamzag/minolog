package com.minolog.api.blog.controller;

import com.minolog.api.auth.domain.AuthenticationPrincipal;
import com.minolog.api.auth.domain.LoginMember;
import com.minolog.api.blog.dto.request.PostCreate;
import com.minolog.api.blog.dto.request.PostSearch;
import com.minolog.api.blog.dto.response.PostResponse;
import com.minolog.api.blog.service.PostService;
import com.minolog.api.blog.dto.request.PostEdit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public ResponseEntity writer(@AuthenticationPrincipal LoginMember loginMember, @RequestBody @Valid PostCreate postCreate) {
        postService.write(postCreate, loginMember.getId());
        return ResponseEntity.created(URI.create("/posts")).build();
    }

    @GetMapping("/{id}")
    public PostResponse get(@PathVariable Long id) {
        return postService.get(id);
    }

    @GetMapping
    public List<PostResponse> getList(PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PutMapping("/{id}")
    public void update(@AuthenticationPrincipal LoginMember loginMember, @PathVariable Long id, @RequestBody PostEdit postEdit) {
        postService.edit(loginMember.getId(), id, postEdit);
    }


}
