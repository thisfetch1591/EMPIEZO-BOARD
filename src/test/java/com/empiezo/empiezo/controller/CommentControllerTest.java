package com.empiezo.empiezo.controller;

import com.empiezo.empiezo.domain.Comment;
import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.domain.Role;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.dto.CommentDto;
import com.empiezo.empiezo.repository.CommentRepository;
import com.empiezo.empiezo.repository.PostRepository;
import com.empiezo.empiezo.repository.UserRepository;
import com.empiezo.empiezo.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 작성")
    void commentWriteTest() throws Exception {

        User user = User.builder()
                .username("pedro1022")
                .password("test1")
                .nickname("손홍민")
                .email("thiago@naver.com")
                .role(Role.ROLE_USER)
                .build();

        Post post = Post.builder()
                .title("test")
                .content("hello babe")
                .writer(user.getNickname())
                .user(user)
                .build();

        userRepository.save(user);
        postRepository.save(post);

        CommentDto.CommentWriteDto request = CommentDto.CommentWriteDto.builder()
                .content("hello")
                .writer(user.getNickname())
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts/{postId}/comments", post.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertEquals(1L, commentRepository.count());

        Comment savedComment = commentRepository.findAll().get(0);
        Assertions.assertEquals("손홍민", savedComment.getWriter());
        Assertions.assertEquals("hello", savedComment.getContent());
    }
}
