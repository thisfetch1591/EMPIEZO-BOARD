package com.empiezo.empiezo.controller;

import com.empiezo.empiezo.dto.ImageDto;
import com.empiezo.empiezo.security.UserPrincipal;
import com.empiezo.empiezo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/uploadProfile")
    public String upload(@ModelAttribute ImageDto.ImageRequest request, @AuthenticationPrincipal UserPrincipal userPrincipal) throws IOException {
        Long userId = userPrincipal.getUserId();
        imageService.uploadImage(request, userId);

        return "redirect:/user";
    }
}
