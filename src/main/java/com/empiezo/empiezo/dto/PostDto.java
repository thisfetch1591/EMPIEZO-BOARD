package com.empiezo.empiezo.dto;

import com.empiezo.empiezo.domain.BooleanState;
import com.empiezo.empiezo.domain.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


public class PostDto {

    @Data
    public static class WriteRequest {

        @NotBlank(message = "게시글 제목을 입력해주세요.")
        private String title;
        private String content;
        private BooleanState isSecret;

        @Builder
        public WriteRequest(String title, String content, BooleanState isSecret) {
            this.title = title;
            this.content = content;
            this.isSecret = isSecret;
        }
    }

    @Data
    @Builder
    public static class ModifyRequest {

        @NotBlank(message = "게시글 제목을 입력해주세요")
        private String title;

        private String content;

        private Long id;

        private BooleanState isSecret;
    }

    @Data
    public static class Response {
        private final long id;

        private final String title;

        private final String content;

        private final String writer;

        private final String username;
        private final Long userId;

        private final String createdDate, modifiedDate;

        private final int views;

        private final BooleanState isSecret;

        private final int likesCount;

        private final List<CommentDto.Response> comments;

        @Builder
        public Response(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.writer = post.getWriter();
            this.username = post.getUsername();
            this.userId = post.getUser().getId();
            this.createdDate = post.getCreatedDate();
            this.modifiedDate = post.getModifiedDate();
            this.views = post.getViews();
            this.isSecret = post.getIsSecret();
            this.likesCount = post.getLikeCount();
            this.comments = post.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());
        }
    }
}
