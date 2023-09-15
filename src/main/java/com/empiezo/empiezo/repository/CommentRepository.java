package com.empiezo.empiezo.repository;

import com.empiezo.empiezo.domain.Comment;
import com.empiezo.empiezo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUser(User user);
}
