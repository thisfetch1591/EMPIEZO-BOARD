package com.empiezo.empiezo.service;

import com.empiezo.empiezo.domain.*;
import com.empiezo.empiezo.dto.UserDto;
import com.empiezo.empiezo.exception.*;
import com.empiezo.empiezo.repository.CommentRepository;
import com.empiezo.empiezo.repository.LikesRepository;
import com.empiezo.empiezo.repository.PostRepository;
import com.empiezo.empiezo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final LikesRepository likesRepository;

    private final CommentRepository commentRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void register(UserDto.RegisterRequest request) throws Exception {
        boolean isDuplicate = validateUniqueUser(request);

        if (isDuplicate) {
            var user = User.builder()
                    .username(request.getUsername())
                    .nickname(request.getNickname())
                    .password(bCryptPasswordEncoder.encode(request.getPassword()))
                    .email(request.getEmail())
                    .role(Role.ROLE_USER)
                    .isSocial(BooleanState.FALSE)
                    .build();
            userRepository.save(user);
        }
    }

    @Transactional(readOnly = true)
    public Page<User> getList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public UserDto.Response get(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return new UserDto.Response(user);
    }
    @Transactional
    public void modify(Long id, UserDto.ModifyRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        user.update(request.getNickname(), request.getPassword());
    }

    @Transactional
    public void delete(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Post> posts = postRepository.findByUser(findUser);

        List<Likes> likes = likesRepository.findByUser(findUser);

        List<Comment> comments = commentRepository.findByUser(findUser);

        userRepository.delete(findUser);
    }

    public boolean validateUniqueUser(UserDto.RegisterRequest request) throws Exception{
        if (userRepository.existsUserByNickname(request.getNickname())) {
            throw new AlreadyExistUserNickname();
        }
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new AlreadyExistUserEmailException();
        }
        if (userRepository.existsUserByUsername(request.getUsername())) {
            throw new AlreadyExistUsernameException();
        }
        return true;
    }
}
