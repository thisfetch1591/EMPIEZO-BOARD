//package com.empiezo.empiezo.controller;
//
//import com.empiezo.empiezo.dto.UserDto;
//import com.empiezo.empiezo.repository.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//class UserControllerTest {
//
//    @Autowired
//    MockMvc mockmvc;
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Transactional
//    @Test
//    @DisplayName("/sign-up 요청시 회원가입 기능 작동")
//    void signupUserTest() throws Exception {
//    }
//}