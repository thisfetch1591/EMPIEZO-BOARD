package com.empiezo.empiezo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class ImageDto {

    @Data
    public static class ImageRequest {
        private Long imageId;

        private MultipartFile imageFile;

    }

    @Data
    public static class ImageResponse {

    }

}
