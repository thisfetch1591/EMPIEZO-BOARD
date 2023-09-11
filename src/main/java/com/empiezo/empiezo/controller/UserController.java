package com.empiezo.empiezo.controller;

import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.dto.UserDto;
import com.empiezo.empiezo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public String register(@Valid @ModelAttribute("dto") UserDto.RegisterRequest dto, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("dto", dto);
            return "auth/register";
        }
        userService.register(dto);
        return "board/boardlist";
    }

    @DeleteMapping("/users/{userId}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/create-user")
    public String registerView(@ModelAttribute("dto") UserDto.RegisterRequest dto) {
        return "auth/register";
    }


    @GetMapping("/users")
    public String userList(
            Model model,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> userList = userService.get(pageable);

        model.addAttribute("users", userList);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", userList.hasNext());
        model.addAttribute("hasPrev",userList.hasPrevious());

        return "admin/adminuser";
    }

    @PostMapping("/login")
    public String errorLogin(@RequestParam("error") String error) {
        return "auth/login";
    }
}
