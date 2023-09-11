package com.empiezo.empiezo.controller;

import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.dto.PostDto;
import com.empiezo.empiezo.dto.UserDto;
import com.empiezo.empiezo.exception.PostNotFoundException;
import com.empiezo.empiezo.repository.PostRepository;
import com.empiezo.empiezo.repository.UserRepository;
import com.empiezo.empiezo.service.PostService;
import com.empiezo.empiezo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void memberInit() {

        User user = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);
        System.out.println("user.getId() = " + user.getId());
    }

    @DisplayName("/post 요청시 글작성 기능 적용")
    @Test
    void postTest() throws Exception {
        PostDto.PostWriteDto dto = PostDto.PostWriteDto.builder()
                .title("hello")
                .content("나는 천재다 나는 개 천 재 다")
                .build();
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/posts")
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        Post Post = postRepository.findById(1L).orElseThrow(PostNotFoundException::new);

        assertEquals(1L, Post.getUser().getId());
    }

    @DisplayName("/posts/{postId} 요청 시 단건 조회 기능 적용")
    @Test
    void getTest() throws Exception {
        User user = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);

        Post post = Post.builder()
                .title("test")
                .content("hello babe")
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(get("/posts/{id}", post.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("/posts 요청 시 List 조회")
    @Test
    void getListTest() throws Exception {
        User user = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);

        Post post1 = Post.builder()
                .title("test")
                .content("hello babe")
                .user(user)
                .build();

        postRepository.save(post1);

        Post post2 = Post.builder()
                .title("test2")
                .content("hello babe2")
                .user(user)
                .build();

        postRepository.save(post2);

        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andDo(print());
    }

    @DisplayName("/posts?page 요청 시 페이징 된 List 조회")
    @Test
    void getPagedList() throws Exception {
        User user = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);

        List<Post> requestPosts = IntStream.range(0,30)
                .mapToObj(i -> Post.builder()
                        .title("test " + i)
                        .content("hello " + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("/posts/{id}요청 시 게시글 수정")
    @Test
    void modPost() throws Exception {
        User user = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);

        Post post = Post.builder()
                .title("수정 전 제목입니다.")
                .content("수정 전 게시글입니다.")
                .user(user)
                .build();
        postRepository.save(post);

        PostDto.PostModify postModify = PostDto.PostModify.builder()
                .title("수정 후 제목입니다.")
                .content("수정 후 게시글입니다.")
                .build();

        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postModify)))
                        .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("/posts/{id}요청 시 게시글 삭제")
    @Test
    void deletePost() throws Exception {
        User user = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);

        Post post = Post.builder()
                .title("수정 전 제목입니다.")
                .content("수정 전 게시글입니다.")
                .user(user)
                .build();
        postRepository.save(post);

        mockMvc.perform(delete("/posts/{postId}", post.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("에러 테스트")
    @Test
    void notFoundTest() throws Exception {
        mockMvc.perform(delete("/posts/{postId}", 1L)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}