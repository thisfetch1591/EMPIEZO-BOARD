package com.empiezo.empiezo.service;

import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.dto.PostDto;
import com.empiezo.empiezo.dto.UserDto;
import com.empiezo.empiezo.exception.PostNotFoundException;
import com.empiezo.empiezo.repository.PostRepository;
import com.empiezo.empiezo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @BeforeEach
    void memberInit() {
    }

    @AfterEach
    void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void postPosting() throws Exception {
        PostDto.PostWriteDto dto = PostDto.PostWriteDto.builder()
                .title("테스트 해볼게요")
                .content("나는 천재다 하하 나를 모두 경배하라 경배하지 않는 자는 다 주거")
                .build();
        postService.write(dto, 1L);

        Post resultPost = postRepository.findById(1L).orElseThrow(PostNotFoundException::new);
        User findUser = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);

        assertEquals(1L, postRepository.count());
        assertEquals(findUser.getId(), resultPost.getUser().getId());
    }

    @Test
    void getTest() throws Exception {
        User user = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);

        Post buildPost = Post.builder()
                .title("Como te llamas")
                .content("mi nombre es Pedro")
                .user(user)
                .build();

        postRepository.save(buildPost);

        PostDto.Response response = postService.get(buildPost.getId());

        assertNotNull(response);
    }

    @Test
    void getListPostTest() throws Exception {
        User user = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);

        List<Post> requestPost = IntStream.range(0, 20)
                        .mapToObj(i -> Post.builder()
                                .title("test " + i)
                                .content("context " + i)
                                .user(user)
                                .build())
                                .collect(Collectors.toList());
        postRepository.saveAll(requestPost);

//        PostDto.PostSearch postSearch = PostDto.PostSearch.builder()
//                .page(1)
//                .build();
//
//        List<PostDto.Response> posts = postService.getList(postSearch);
//
//        assertEquals(10L, posts.size());
//        assertEquals("test 19", posts.get(0).getTitle());
    }

    @Test
    void postModify() throws Exception {
        User user = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);
        Post buildPost = Post.builder()
                .title("수정 테스트 게시글 입니다.")
                .content("이건 수정 전입니다.")
                .user(user)
                .build();
        postRepository.save(buildPost);

        Post post = postRepository.findById(buildPost.getId())
                .orElseThrow(PostNotFoundException::new);

        PostDto.PostModify postModify = PostDto.PostModify.builder()
                .title("")
                .content("이건 수정 후 입니다.")
                .build();

        post.modify(postModify.getTitle(), postModify.getContent());

        assertNotEquals("이건 수정 전입니다.", post.getContent());
    }

    @Test
    void deletePost() throws Exception {
        User user = userRepository.findById(1L).orElseThrow(NoSuchElementException::new);
        Post buildPost = Post.builder()
                .title("수정 테스트 게시글 입니다.")
                .content("이건 수정 전입니다.")
                .user(user)
                .build();
        postRepository.save(buildPost);

        Post post = postRepository.findById(buildPost.getId())
                .orElseThrow(PostNotFoundException::new);

        postService.delete(post.getId());

        assertEquals(0, postRepository.count());
    }
}