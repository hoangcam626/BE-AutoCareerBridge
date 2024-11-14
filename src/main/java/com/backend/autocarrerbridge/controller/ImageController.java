package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.image.sdo.ImageUploadSdo;
import com.backend.autocarrerbridge.dto.image.sdo.ImageSdo;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;
    @PostMapping("/upload")
    public ApiResponse<ImageUploadSdo> uploadImage(@RequestParam("req") MultipartFile req) {

        var res = ImageUploadSdo.of(imageService.uploadFile(req));
        return new ApiResponse<>(res);
    }
    @PostMapping("/upload-images")
    public ApiResponse<List<Long>> uploadImages(@RequestParam("reqs") MultipartFile[] reqs) {
        var res = imageService.uploadFiles(reqs);
        return new ApiResponse<>(res);
    }

    @GetMapping("/resource")
    public ResponseEntity<Resource> getImage(@RequestParam("imageId") Long id) throws IOException {

        ImageSdo imageSdo = imageService.getResource(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, imageSdo.getMediaType())
                .body(imageSdo.getResource());
    }
    @DeleteMapping("/delete")
    public ApiResponse<?> deleteImage(@RequestParam("imageId") Long id) {
        imageService.delete(id);
        return new ApiResponse<>();
    }
}