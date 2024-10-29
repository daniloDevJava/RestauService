package com.projet.foodGo.service;

import com.projet.foodGo.dto.ImagesDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import org.springframework.http.codec.multipart.FilePart;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface ImageService {
    Mono<ImagesDto> createImage(UUID productId, FilePart file);
    boolean deleteImage(UUID image_id);
    ImagesDto updateImage(UUID product_id,ImagesDto imagesDto);
    List<ImagesDto> getAll(UUID image_id);
}
