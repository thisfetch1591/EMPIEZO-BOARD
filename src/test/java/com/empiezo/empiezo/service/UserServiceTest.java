package com.empiezo.empiezo.service;

import com.empiezo.empiezo.domain.BooleanState;
import com.empiezo.empiezo.domain.Role;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.dto.UserDto;
import com.empiezo.empiezo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원 가입 테스트")
    void memberJoinTest() throws Exception {
        // given
        UserDto.RegisterRequest request = UserDto.RegisterRequest.builder()
                .username("testUsername")
                .password("testPassword")
                .nickname("testNickname")
                .email("test@naver.com")
                .build();

        // when
        userService.register(request);

        // then
        assertEquals(1L, userRepository.count());
        User user = userRepository.findAll().get(0);
        assertEquals("testUsername", user.getUsername());
    }

    @Test
    @DisplayName("회원 단건 조회 테스트")
    void memberGetTest() throws Exception {
        // given
        User user = User.builder()
                .username("testsUsername")
                .password("159123125")
                .nickname("foo")
                .email("hello@naver.com")
                .isSocial(BooleanState.FALSE)
                .isDeleted(BooleanState.FALSE)
                .build();

        userRepository.save(user);

        // when
        UserDto.Response response = userService.get(user.getId());

        // then
        assertNotNull(response);
        assertEquals(1L, userRepository.count());
        assertEquals("foo", response.getNickname());
    }

    @Test
    @DisplayName("회원 목록 조회")
    void memberListTest() throws Exception {
        // given
        List<User> request = IntStream.range(0, 20)
                .mapToObj(i -> User.builder()
                        .username("title" +i)
                        .nickname("nick" + i)
                        .password("password" + i)
                        .email("pedro" + i + "@naver.com")
                        .role(Role.ROLE_USER)
                        .isSocial(BooleanState.FALSE)
                        .isDeleted(BooleanState.FALSE)
                        .build())
                .collect(Collectors.toList());

        // when
        userRepository.saveAll(request);
        Pageable pageable = PageRequest.of(0, 20);
        Page<User> response = userService.getList(pageable);
        List<User> users = response.getContent();

        // then
        assertEquals(20L, response.getSize());
        assertEquals("title0", users.get(1).getUsername());
    }
}