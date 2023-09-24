package com.empiezo.empiezo.repository;

import com.empiezo.empiezo.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
