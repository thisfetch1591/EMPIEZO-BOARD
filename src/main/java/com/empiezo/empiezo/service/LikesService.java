package com.empiezo.empiezo.service;

import com.empiezo.empiezo.domain.Likes;
import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.exception.PostNotFoundException;
import com.empiezo.empiezo.exception.UserNotFoundException;
import com.empiezo.empiezo.repository.LikesRepository;
import com.empiezo.empiezo.repository.PostRepository;
import com.empiezo.empiezo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Transactional
    public void manipulateLike(Long userId, Long postId) throws IllegalArgumentException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        Optional<Likes> findLike = likesRepository.findByUserAndPost(user, post);

        if(findLike.isPresent()) {
            likesRepository.deleteById(findLike.get().getId());
        } else{
            Likes like = Likes.builder()
                    .user(user)
                    .post(post)
                    .build();
            likesRepository.save(like);
        }

        postRepository.decreaseView(post.getId());
    }
}
