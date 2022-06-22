package com.minolog.api.controller;

import com.minolog.api.dto.request.PostCreate;
import com.minolog.api.dto.request.PostEdit;
import com.minolog.api.dto.request.PostSearch;
import com.minolog.api.dto.response.PostResponse;
import com.minolog.api.service.PostService;
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
    public ResponseEntity writer(@RequestBody @Valid PostCreate postCreate) {
        postService.write(postCreate);
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
    public void update(@PathVariable Long id, @RequestBody PostEdit postEdit) {
        postService.edit(id, postEdit);
    }


}
