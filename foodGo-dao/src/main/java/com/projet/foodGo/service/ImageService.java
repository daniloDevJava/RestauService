package com.projet.foodGo.service;

import com.projet.foodGo.dto.ImagesDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * The interface Image service.
 */
@Service
public interface ImageService {
    /**
     * Create image images dto.
     *
     * @param productId the product id
     * @param file      the file
     * @return the images dto
     * @throws IOException the io exception
     */
    ImagesDto createImage(UUID productId, MultipartFile file) throws IOException;

    /**
     * Delete image boolean.
     *
     * @param image_id the image id
     * @return the boolean
     */
    boolean deleteImage(Long image_id);

    /**
     * Gets image.
     *
     * @param imageId the image id
     * @return the image
     */
    ImagesDto getImage(Long imageId);

    /**
     * Update name images dto.
     *
     * @param imageId   the image id
     * @param imagesDto the images dto
     * @return the images dto
     */
    ImagesDto updateName(Long imageId,ImagesDto imagesDto);

    /**
     * Gets image.
     *
     * @param nom       the nom
     * @param productId the product id
     * @return the image
     */
    ImagesDto getImage(String nom, UUID productId);

    /**
     * Update image images dto.
     *
     * @param imageId   the image id
     * @param imagesDto the images dto
     * @param file      the file
     * @return the images dto
     * @throws IOException the io exception
     */
    ImagesDto updateImage(Long imageId, ImagesDto imagesDto, MultipartFile file) throws IOException;

    /**
     * Gets all.
     *
     * @param productId the product id
     * @return the all
     */
    List<ImagesDto> getAll(UUID productId);
}
