package com.minolog.api.blog.service;

import com.minolog.api.blog.domain.Post;
import com.minolog.api.blog.dto.request.PostCreate;
import com.minolog.api.blog.dto.request.PostEdit;
import com.minolog.api.blog.dto.request.PostSearch;
import com.minolog.api.blog.dto.response.PostResponse;
import com.minolog.api.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setup() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목임")
                .content("내용임")
                .build();

        // when
        postService.write(postCreate, 1L);

        // then
        assertThat(postRepository.count()).isEqualTo(1L);
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        PostCreate request = PostCreate.builder()
                .title("글 제목이다")
                .content("내용")
                .build();
        Post write = postService.write(request, 1L);

        // when
        PostResponse post = postService.get(write.getId());

        // then
        assertThat(post.getTitle()).isEqualTo("글 제목이다");
        assertThat(post.getContent()).isEqualTo("내용");
    }

    @Test
    @DisplayName("글 1개 조회")
    void test3() throws Exception {
        // given
        Post request = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", request.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(request.getId()))
                .andExpect(jsonPath("$.title").value("foo"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
        // then
    }

    @Test
    @DisplayName("글 여러 개 조회 페이징")
    void test4() throws Exception {
        // given
        List<Post> requests = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requests);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                //.size(10)
                .build();

        // when
        List<PostResponse> list = postService.getList(postSearch);

        // then
        assertThat(list).hasSize(10);
        assertThat(list.get(0).getTitle()).isEqualTo("제목 30");
        assertThat(list.get(4).getTitle()).isEqualTo("제목 26");
    }

    @Test
    @DisplayName("수정")
    void editTest() {
        List<Post> requests = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requests);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();
        List<PostResponse> list = postService.getList(postSearch);
        // when
        postService.edit(list.get(0).getId(), list.get(0).getId(), PostEdit.builder().title("뭐").content("반포자이 30").build());
        postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();
        list = postService.getList(postSearch);

        assertThat(list.get(0).getTitle()).isEqualTo("뭐");
        assertThat(list.get(0).getContent()).isEqualTo("반포자이 30");
    }
}
