package com.empiezo.empiezo.controller;

import com.empiezo.empiezo.config.UserPrincipal;
import com.empiezo.empiezo.dto.CommentDto;
import com.empiezo.empiezo.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글 API", description = "댓글에 대한 CRUD API")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description="로그인 중인 user, 해당 포스트의 id, DTO를 파라미터로 받습니다.")
    @PostMapping("/posts/{postId}/comments")
    public void write(@PathVariable Long postId,
                      @AuthenticationPrincipal UserPrincipal userPrincipal,
                      @RequestBody CommentDto.WriteRequest request) {
        commentService.write(request, userPrincipal.getUserId(), postId);
    }

    @Operation(summary = "댓글 수정", description="수정하고자 하는 댓글의 id, DTO를 파라미터로 받습니다.")
    @PostMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto.ModifyRequest> modify(@PathVariable Long commentId, @RequestBody @Valid CommentDto.ModifyRequest request) {
        commentService.modify(commentId, request);
        return ResponseEntity.ok(request);
    }
    @Operation(summary = "댓글 삭제", description="수정하고자 하는 댓글의 id를 파라미터로 받습니다.")
    @DeleteMapping("/comments/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }
}
