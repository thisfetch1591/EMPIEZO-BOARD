package com.empiezo.empiezo.controller;

import com.empiezo.empiezo.config.UserPrincipal;
import com.empiezo.empiezo.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/posts/{postId}/likes")
    public void writeOrDelete(@PathVariable Long postId,
                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        likesService.manipulateLike(userPrincipal.getUserId(), postId);
    }
}
