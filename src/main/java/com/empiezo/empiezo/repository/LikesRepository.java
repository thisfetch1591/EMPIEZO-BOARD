package com.empiezo.empiezo.repository;

import com.empiezo.empiezo.domain.Likes;
import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserAndPost(User user, Post post);
}
