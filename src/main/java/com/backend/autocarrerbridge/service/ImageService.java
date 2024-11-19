package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.image.sdo.ImageSdo;
import com.backend.autocarrerbridge.entity.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ImageService {
    Image getImage(Integer id);
    String getPathImage(Integer id);
    Integer uploadFile(MultipartFile req);
    List<Integer> uploadFiles(MultipartFile[] reqs);
    void delete(Integer id);
    ImageSdo getResource(Integer id) throws IOException;
}