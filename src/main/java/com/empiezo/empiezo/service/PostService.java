package com.empiezo.empiezo.service;

import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.dto.PostDto;
import com.empiezo.empiezo.exception.PostNotFoundException;
import com.empiezo.empiezo.exception.UserNotFoundException;
import com.empiezo.empiezo.repository.PostRepository;
import com.empiezo.empiezo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public Long write(PostDto.WriteRequest dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(user.getNickname())
                .user(user)
                .isSecret(dto.getIsSecret())
                .build();

        postRepository.save(post);

        return user.getId();
    }

    @Transactional(readOnly = true)
    public PostDto.Response get(Long id) {
        postRepository.increaseView(id);
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return new PostDto.Response(post);
    }

    @Transactional(readOnly = true)
    public Page<Post> get(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Post> searchByTitle(String title, Pageable pageable) {
        return postRepository.findByTitleContaining(title, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Post> searchByWriter(String writer, Pageable pageable) {
        return postRepository.findByWriterContaining(writer, pageable);
    }

    @Transactional
    public void modify(Long id, PostDto.ModifyRequest modifyRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        post.modify(modifyRequest.getTitle(), modifyRequest.getContent());
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        postRepository.delete(post);
    }
}
