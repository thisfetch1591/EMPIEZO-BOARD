package com.empiezo.empiezo.repository;

import com.empiezo.empiezo.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
