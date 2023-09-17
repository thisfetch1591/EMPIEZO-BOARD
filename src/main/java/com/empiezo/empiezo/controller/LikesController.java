package com.empiezo.empiezo.controller;

import com.empiezo.empiezo.security.UserPrincipal;
import com.empiezo.empiezo.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "추천 API", description = "추천에 대한 Create or Delete API")
@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;

    @Operation(summary = "좋아요 추가 및 삭제", description="좋아요를 남길 post의 id값, 로그인 중인 user를 파라미터로 넘깁니다.")
    @PostMapping("/posts/{postId}/likes")
    public void writeOrDelete(@PathVariable Long postId,
                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        likesService.manipulateLike(userPrincipal.getUserId(), postId);
    }
}
