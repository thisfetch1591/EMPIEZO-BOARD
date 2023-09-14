package com.empiezo.empiezo.service;

import com.empiezo.empiezo.domain.Comment;
import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.dto.CommentDto;
import com.empiezo.empiezo.exception.CommentNotFoundException;
import com.empiezo.empiezo.exception.PostNotFoundException;
import com.empiezo.empiezo.exception.UserNotFoundException;
import com.empiezo.empiezo.repository.CommentRepository;
import com.empiezo.empiezo.repository.PostRepository;
import com.empiezo.empiezo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public void write(CommentDto.WriteRequest dto, Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        postRepository.decreaseView(post.getId());
        String writer = user.getNickname();

        Comment comment = Comment.builder()
                .writer(writer)
                .username(user.getUsername())
                .content(dto.getContent())
                .user(user)
                .post(post)
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void modify(Long id, CommentDto.ModifyRequest modifyRequest) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        comment.modify(modifyRequest.getModifyContent());
    }

    @Transactional
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        commentRepository.delete(comment);
    }
}
