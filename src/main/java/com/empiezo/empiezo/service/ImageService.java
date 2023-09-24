package com.empiezo.empiezo.service;

import com.empiezo.empiezo.domain.Image;
import com.empiezo.empiezo.domain.User;
import com.empiezo.empiezo.dto.ImageDto;
import com.empiezo.empiezo.exception.UserNotFoundException;
import com.empiezo.empiezo.repository.ImageRepository;
import com.empiezo.empiezo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${file.path}")
    private String fileDir;

    private final ImageRepository imageRepository;

    private final UserRepository userRepository;

    @Transactional
    public void uploadImage(ImageDto.ImageRequest dto, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        MultipartFile image = dto.getImageFile();
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + image.getOriginalFilename();

        File fullNameImage = new File(fileDir + imageFileName);

        if (!image.isEmpty()) {
            image.transferTo(fullNameImage);

            Optional<Image> findImage = imageRepository.findById(userId);
            if (findImage.isPresent()) {
                findImage.get().updateUrl(fileDir + imageFileName);
            } else {
                Image newImage = Image.builder()
                        .user(user)
                        .url(fileDir + imageFileName)
                        .build();
                findImage = Optional.of(newImage);
            }
            imageRepository.save(findImage.get());
        }
    }


}
