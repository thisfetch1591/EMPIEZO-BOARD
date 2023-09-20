package com.empiezo.empiezo.repository;

import com.empiezo.empiezo.domain.BooleanState;
import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    boolean existsUserByUsername(String username);

    boolean existsUserByNickname(String nickname);

    Page<User> findByIsDeleted(BooleanState booleanState, Pageable pageable);
}
