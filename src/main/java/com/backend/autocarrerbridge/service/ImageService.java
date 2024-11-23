package com.backend.autocarrerbridge.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.autocarrerbridge.dto.response.image.ImageResponse;
import com.backend.autocarrerbridge.entity.Image;

public interface ImageService {
    Image getImage(Integer id);

    String getPathImage(Integer id);

    Integer uploadFile(MultipartFile req);

    List<Integer> uploadFiles(MultipartFile[] reqs);

    void delete(Integer id);

    ImageResponse getResource(Integer id) throws IOException;
}
