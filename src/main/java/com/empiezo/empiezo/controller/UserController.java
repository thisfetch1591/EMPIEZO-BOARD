package com.empiezo.empiezo.controller;

import com.empiezo.empiezo.config.UserPrincipal;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.dto.UserDto;
import com.empiezo.empiezo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 API", description = "사용자에 대한 CRUD API")
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
        return "auth/login";
    }

    @PostMapping("/user/modify")
    public String modify(@Valid @ModelAttribute("user") UserDto.ModifyRequest dto,
                                                        BindingResult bindingResult,
                                                        Model model) throws Exception{
        if (bindingResult.hasErrors()) {
            Long authUserId = dto.getId();
            UserDto.Response response = userService.get(authUserId);
            model.addAttribute("user", response);
            return "auth/usermodify";
        }
        userService.modify(dto.getId(), dto);
        return "redirect:/posts";
    }

    @Operation(summary = "사용자 삭제", description="사용자의 id를 파라미터로 받습니다. Admin만 사용 가능합니다.")
    @DeleteMapping("/users/{userId}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login() {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        if (authUser != null && !(authUser instanceof AnonymousAuthenticationToken) && authUser.isAuthenticated()) {
            return "redirect:/posts";
        }
        return "auth/login";
    }

    @GetMapping("/create-user")
    public String registerView(@ModelAttribute("dto") UserDto.RegisterRequest dto) {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();

        if (authUser != null && !(authUser instanceof AnonymousAuthenticationToken) && authUser.isAuthenticated()) {
            return "redirect:/posts";
        }
        return "auth/register";
    }

    @GetMapping("/user")
    public String modifyUser(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        Long authUserId = userPrincipal.getUserId();

        UserDto.Response response = userService.get(authUserId);

        model.addAttribute("user", response);
        return "auth/usermodify";
    }


    @GetMapping("/admin/users")
    public String userList(
            Model model,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> userList = userService.getList(pageable);

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
