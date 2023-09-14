package com.empiezo.empiezo.controller;

import com.empiezo.empiezo.config.UserPrincipal;
import com.empiezo.empiezo.dto.CommentDto;
import com.empiezo.empiezo.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public void write(@PathVariable Long postId,
                      @AuthenticationPrincipal UserPrincipal userPrincipal,
                      @RequestBody CommentDto.WriteRequest request) {
        commentService.write(request, userPrincipal.getUserId(), postId);
    }

    @PostMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto.ModifyRequest> modify(@PathVariable Long commentId, @RequestBody @Valid CommentDto.ModifyRequest request) {
        commentService.modify(commentId, request);
        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/comments/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }
}
