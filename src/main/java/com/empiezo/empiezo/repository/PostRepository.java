package com.empiezo.empiezo.repository;

import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.id = :id")
    int increaseView(Long id);

    @Modifying
    @Query("UPDATE Post p SET p.views = p.views - 1 WHERE p.id = :id")
    int decreaseView(Long id);

    List<Post> findByUser(User user);

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    Page<Post> findByWriterContaining(String writer, Pageable pageable);
}
