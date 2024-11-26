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

/**
 * ImageController quản lý các API liên quan đến việc xử lý tệp hình ảnh.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;

    /**
     * API tải lên một tệp hình ảnh.
     *
     * @param req - Tệp hình ảnh cần tải lên.
     * @return ApiResponse chứa thông tin của hình ảnh vừa được tải lên (bao gồm ID và đường dẫn lưu trữ).
     */
    @PostMapping("/upload")
    public ApiResponse<ImageUploadSdo> uploadImage(@RequestParam("req") MultipartFile req) {

        var res = ImageUploadSdo.of(imageService.uploadFile(req));
        return new ApiResponse<>(res);
    }

    /**
     * API tải lên nhiều tệp hình ảnh cùng lúc.
     *
     * @param reqs - Danh sách các tệp hình ảnh cần tải lên.
     * @return ApiResponse chứa danh sách các ID của hình ảnh vừa được tải lên.
     */
    @PostMapping("/upload-images")
    public ApiResponse<List<Integer>> uploadImages(@RequestParam("reqs") MultipartFile[] reqs) {
        var res = imageService.uploadFiles(reqs);
        return new ApiResponse<>(res);
    }

    /**
     * API lấy tệp hình ảnh từ hệ thống thông qua ID.
     *
     * @param id - ID của hình ảnh cần lấy.
     * @return ResponseEntity chứa dữ liệu hình ảnh và các thông tin liên quan như loại nội dung (Content-Type).
     * @throws IOException - Nếu xảy ra lỗi trong quá trình truy xuất tệp.
     */
    @GetMapping("/resource")
    public ResponseEntity<Resource> getImage(@RequestParam("imageId") Integer id) throws IOException {

        ImageResponse imageResponse = imageService.getResource(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, imageResponse.getMediaType())
                .body(imageResponse.getResource());
    }

    /**
     * API xóa hình ảnh khỏi hệ thống dựa trên ID.
     *
     * @param id - ID của hình ảnh cần xóa.
     * @return ApiResponse xác nhận hình ảnh đã được xóa thành công.
     */
    @DeleteMapping("/delete")
    public ApiResponse<Object> deleteImage(@RequestParam("imageId") Integer id) {
        imageService.delete(id);
        return new ApiResponse<>();
    }
}
