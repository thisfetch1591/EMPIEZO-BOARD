package com.empiezo.empiezo.service;

import com.empiezo.empiezo.domain.BooleanState;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.dto.UserDto;
import com.empiezo.empiezo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
        //given
        UserDto.RegisterRequest request = UserDto.RegisterRequest.builder()
                .username("testUsername")
                .password("testPassword")
                .nickname("testNickname")
                .email("test@naver.com")
                .build();

        //when
        userService.register(request);

        //then
        assertEquals(1L, userRepository.count());
        User user = userRepository.findAll().get(0);
        assertEquals("testUsername", user.getUsername());
    }

    @Test
    @DisplayName("회원 단건 조회 테스트")
    void memberGetTest() throws Exception {
        //given
        User user = User.builder()
                .username("testsUsername")
                .password("159123125")
                .nickname("foo")
                .email("hello@naver.com")
                .isSocial(BooleanState.FALSE)
                .isDeleted(BooleanState.FALSE)
                .build();

        userRepository.save(user);

        //when
        UserDto.Response response = userService.get(user.getId());

        // then
        assertNotNull(response);
        assertEquals(1L, userRepository.count());
        assertEquals("foo", response.getNickname());
    }

}