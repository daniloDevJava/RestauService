package com.projet.foodGo.service;

import com.projet.foodGo.dto.ImagesDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface ImageService {
    ImagesDto createImage(UUID productId, MultipartFile file) throws IOException;
    boolean deleteImage(Long image_id);
    ImagesDto updateImage(UUID product_id,ImagesDto imagesDto);
    List<ImagesDto> getAll(UUID productId);
}
