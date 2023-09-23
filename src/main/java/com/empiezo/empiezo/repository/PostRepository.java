package com.empiezo.empiezo.repository;

import com.empiezo.empiezo.domain.BooleanState;
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

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    Page<Post> findByWriterContaining(String writer, Pageable pageable);

    @Query(value = "SELECT p " +
            "FROM Post p " +
            "LEFT JOIN Likes l ON p.id = l.post.id " +
            "WHERE FUNCTION('DATE_FORMAT', CURRENT_DATE, '%Y.%m.%d') = SUBSTRING(l.createdDate, 1, 10) " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(l.id) DESC")
    List<Post> findTodayBestLikePost();

}
