package com.empiezo.empiezo.dto;

import com.empiezo.empiezo.domain.Comment;
import com.empiezo.empiezo.domain.Post;
import com.empiezo.empiezo.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class CommentDto {

    @Data
    public static class CommentWriteDto {

        @NotBlank(message = "댓글 내용을 입력해주세요.")
        private String content;

        @NotBlank(message = "작성자가 존재하지 않습니다.")
        private String writer;

        @Builder
        public CommentWriteDto(String content, String writer) {
            this.content = content;
            this.writer = writer;
        }

    }

    @Data
    public static class Response {
        private Long id;
        private String context;
        private String createdDate;
        private String writer;
        private Long userId;
        private Long postId;

        public Response(Comment comment) {
            this.id = comment.getId();
            this.context = comment.getContent();
            this.createdDate = comment.getCreatedDate();
            this.writer = comment.getWriter();
            this.userId = comment.getUser().getId();
            this.postId = comment.getPost().getId();
        }
    }
}
