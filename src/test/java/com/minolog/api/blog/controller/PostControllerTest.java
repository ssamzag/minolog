package com.minolog.api.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minolog.api.auth.dto.TokenRequest;
import com.minolog.api.auth.dto.TokenResponse;
import com.minolog.api.auth.service.AuthService;
import com.minolog.api.blog.domain.Post;
import com.minolog.api.blog.dto.request.PostCreate;
import com.minolog.api.blog.repository.PostRepository;
import com.minolog.api.member.dto.MemberRequest;
import com.minolog.api.member.dto.MemberResponse;
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

    private MemberResponse memberResponse;
    private String accessToken;

    @BeforeEach
    void setup() {
        postRepository.deleteAll();
        memberResponse = memberService.save(MemberRequest.builder().userId("hi").nickName("myNick").password("1234").build());
        TokenResponse hi = authService.login(TokenRequest.builder().userId("hi").password("1234").build());
        accessToken = hi.getAccessToken();
    }

    @Test
    @DisplayName("/posts 요청시 title은 필수")
    void test() throws Exception {
        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"\", \"content\":\"내용입니다.\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title은 필수")
    void test2() throws Exception {
        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        assertThat(postRepository.count()).isEqualTo(1L);

    }

    @Test
    @DisplayName("/posts 요청시 db에 값이 저장된다")
    void test3() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다.")
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
        assertThat(post.getTitle()).isEqualTo("제목입니다");
        assertThat(post.getContent()).isEqualTo("내용입니다.");
    }

    @Test
    @DisplayName("/posts 요청시 db에 값이 저장된다")
    void test4() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다.")
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
        assertThat(post.getTitle()).isEqualTo("제목입니다");
        assertThat(post.getContent()).isEqualTo("내용입니다.");
    }

    @Test
    @DisplayName("글 여러 개 조회")
    void test5() throws Exception {
        //given
        Post request = Post.builder()
                .title("제목입니다")
                .content("내용입니다.")
                .build();
        Post request2 = Post.builder()
                .title("제목입니다2")
                .content("내용입니다.2")
                .build();
        postRepository.saveAll(Arrays.asList(request, request2));

        //expected
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러 개 조회 페이징")
    void test6() throws Exception {
        //given
        List<Post> requests = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("반포자이 " + i)
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
                .andExpect(jsonPath("$[0].title").value("제목 30"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 9으로 요청하면 첫 페이지를 가져온다.")
    void test76() throws Exception {
        //given
        List<Post> requests = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("반포자이 " + i)
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
                .andExpect(jsonPath("$[0].title").value("제목 30"))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 로그인을 시도하면 에러를 반환한다")
    void loginTest1() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(TokenRequest.builder().userId("you").build());

        //expected
        mockMvc.perform(post("/login/token")
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("아이디를 확인해 주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("틀린 비밀번호로 로그인을 시도하면 에러를 반환한다")
    void loginTest2() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(TokenRequest.builder().userId("ssamzag").password("what").build());

        //expected
        mockMvc.perform(post("/login/token")
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("비밀번호가 틀림"))
                .andDo(print());
    }
}
