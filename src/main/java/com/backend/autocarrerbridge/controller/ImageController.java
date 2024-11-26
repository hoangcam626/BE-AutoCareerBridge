package com.backend.autocarrerbridge.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.autocarrerbridge.dto.ApiResponse;
import com.backend.autocarrerbridge.dto.response.image.ImageResponse;
import com.backend.autocarrerbridge.dto.response.image.ImageUploadSdo;
import com.backend.autocarrerbridge.service.ImageService;

import lombok.RequiredArgsConstructor;

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
    public ApiResponse<List<Integer>> uploadImages(@RequestParam("reqs") MultipartFile[] reqs) {
        var res = imageService.uploadFiles(reqs);
        return new ApiResponse<>(res);
    }

    @GetMapping("/resource")
    public ResponseEntity<Resource> getImage(@RequestParam("imageId") Integer id) throws IOException {

        ImageResponse imageResponse = imageService.getResource(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, imageResponse.getMediaType())
                .body(imageResponse.getResource());
    }

    @DeleteMapping("/delete")
    public ApiResponse<Object> deleteImage(@RequestParam("imageId") Integer id) {
        imageService.delete(id);
        return new ApiResponse<>();
    }
}
