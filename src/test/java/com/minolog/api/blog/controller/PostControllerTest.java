package com.minolog.api.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minolog.api.auth.dto.TokenRequest;
import com.minolog.api.auth.dto.TokenResponse;
import com.minolog.api.auth.service.AuthService;
import com.minolog.api.blog.domain.Post;
import com.minolog.api.blog.dto.request.PostCreate;
import com.minolog.api.blog.dto.request.PostSearch;
import com.minolog.api.blog.repository.PostRepository;
import com.minolog.api.member.domain.MemberRepository;
import com.minolog.api.member.dto.MemberRequest;
import com.minolog.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthService authService;
    @Autowired
    private MemberRepository memberRepository;

    private String accessToken;

    @BeforeEach
    void setup() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
        memberService.save(MemberRequest.builder().userId("hi").nickName("myNick").password("1234").build());
        TokenResponse hi = authService.login(TokenRequest.builder().userId("hi").password("1234").build());
        accessToken = hi.getAccessToken();
    }

    @Test
    @DisplayName("/posts ????????? title??? ??????")
    void test() throws Exception {
        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"\", \"content\":\"???????????????.\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("????????? ???????????????."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts ????????? title??? ??????")
    void test2() throws Exception {
        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\": \"???????????????\", \"content\": \"???????????????.\"}")
                )
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        assertThat(postRepository.count()).isEqualTo(1L);

    }

    @Test
    @DisplayName("/posts ????????? db??? ?????? ????????????")
    void test3() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("???????????????")
                .content("???????????????.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("???????????????");
        assertThat(post.getContent()).isEqualTo("???????????????.");
    }

    @Test
    @DisplayName("/posts ????????? db??? ?????? ????????????")
    void test4() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("???????????????")
                .content("???????????????.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("???????????????");
        assertThat(post.getContent()).isEqualTo("???????????????.");
    }

    @Test
    @DisplayName("??? ?????? ??? ??????")
    void test5() throws Exception {
        //given
        Post request = Post.builder()
                .title("???????????????")
                .content("???????????????.")
                .build();
        Post request2 = Post.builder()
                .title("???????????????2")
                .content("???????????????.2")
                .build();
        postRepository.saveAll(Arrays.asList(request, request2));

        //expected
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());
    }

    @Test
    @DisplayName("??? ?????? ??? ?????? ?????????")
    void test6() throws Exception {
        //given
        List<Post> requests = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("?????? " + i)
                        .content("???????????? " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requests);
        // when

        //expected
        mockMvc.perform(get("/posts?page=1&size=20")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].title").value("?????? 30"))
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? 9?????? ???????????? ??? ???????????? ????????????.")
    void test76() throws Exception {
        //given
        List<Post> requests = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("?????? " + i)
                        .content("???????????? " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requests);
        // when

        //expected
        mockMvc.perform(get("/posts?page=0&size=20")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$[0].title").value("?????? 30"))
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ?????? ???????????? ???????????? ???????????? ????????? ????????????")
    void loginTest1() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(TokenRequest.builder().userId("bye").build());

        //expected
        mockMvc.perform(post("/login/token")
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("???????????? ????????? ?????????."))
                .andDo(print());
    }

    @Test
    @DisplayName("?????? ??????????????? ???????????? ???????????? ????????? ????????????")
    void loginTest2() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(TokenRequest.builder().userId("hi").password("what").build());

        //expected
        mockMvc.perform(post("/login/token")
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("??????????????? ??????"))
                .andDo(print());
    }
}
