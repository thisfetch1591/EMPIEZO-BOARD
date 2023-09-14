package com.empiezo.empiezo.dto;

import lombok.Builder;
import lombok.Data;

public class LikesDto {

    @Data
    public static class WriteRequest {

        private Long userId;
        private Long postId;

        @Builder
        public WriteRequest(Long userId, Long postId) {
            this.userId = userId;
            this.postId = postId;
        }
    }
}
