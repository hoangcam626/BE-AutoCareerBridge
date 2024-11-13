package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.image.sdo.ImageSdo;
import com.backend.autocarrerbridge.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("req") MultipartFile req) {

        Long imgId = imageService.uploadFile(req);
        return ResponseEntity.ok("Success: " + imgId);
    }
    @PostMapping("/upload-images")
    public ResponseEntity<List<Long>> uploadImages(@RequestParam("reqs") MultipartFile[] reqs) {

        return ResponseEntity.ok(imageService.uploadFiles(reqs));
    }

    @GetMapping("/resource")
    public ResponseEntity<Resource> getImage(@RequestParam("imageId") Long id) throws IOException {

        ImageSdo imageSdo = imageService.getResource(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, imageSdo.getMediaType())
                .body(imageSdo.getResource());
    }
}