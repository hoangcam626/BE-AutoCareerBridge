package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.image.sdo.ImageSdo;
import com.backend.autocarrerbridge.entity.Image;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.repository.ImageRepository;
import com.backend.autocarrerbridge.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.backend.autocarrerbridge.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Value("${media.img_path}")
    private String imgFolder;
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private static final List<String> ACCEPTED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif"
    );

    public Image getImage(Integer id) {
        return imageRepository.findById(id).orElseThrow(() -> new AppException((ERROR_FIND_IMAGE)));
    }

    public String getPathImage(Integer id) {

        return imageRepository.findById(id)
                .map(image -> imgFolder + File.separator + image.getFilename())
                .orElse("");
    }

    public Integer uploadFile(MultipartFile req) {

        validateFile(req);

        try {
            createDirectoryIfNotExists(imgFolder);

            String fileName = String.format("%s%s", UUID.randomUUID(), getFileExtension(req.getOriginalFilename()));
            Path filePath = Paths.get(imgFolder, fileName);

            try (OutputStream outputStream = new FileOutputStream(filePath.toFile())) {
                outputStream.write(req.getBytes());
            }

            Image image = imageRepository.save(Image.builder()
                    .filename(fileName)
                    .type(req.getContentType())
                    .build());
            return image.getId();
        } catch (IOException e) {
            throw new AppException(ERROR_SAVE_FILE);
        }
    }

    public List<Integer> uploadFiles(MultipartFile[] reqs) {
        return Arrays.stream(reqs).map(this::uploadFile).collect(Collectors.toList());
    }

    public void delete(Integer id) {

        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new AppException(ERROR_FIND_IMAGE));
        Path filePath = Paths.get(imgFolder, image.getFilename());
        try {
            Files.deleteIfExists(filePath);
            imageRepository.deleteById(id);
        } catch (Exception e) {
            throw new AppException(ERROR_DELETE_IMAGE);
        }
    }

    public ImageSdo getResource(Integer id) throws IOException {
        Path path = Paths.get(getPathImage(id));
        Image media = getImage(id);
        Resource resource = new UrlResource(path.toUri());

        if (resource.exists()) {
            return ImageSdo.of(resource, media.getType());
        } else {
            throw new AppException(ERROR_FIND_IMAGE);
        }
    }

    public String getFileExtension(String fileName) {
        return "." + FilenameUtils.getExtension(fileName);
    }

    private void createDirectoryIfNotExists(String folderPath) {
        File dir = new File(folderPath);
        if (!dir.exists() ) {
            boolean checkCreated = dir.mkdirs();
            if (!checkCreated) {
                throw new AppException(ERROR_DIRECTORY_FILE);
            }
        }
    }

    private void validateFile(MultipartFile req) {
        if (req.isEmpty()) {
            throw new AppException(ERROR_EMPTY_FILE);
        }
        if (!ACCEPTED_CONTENT_TYPES.contains(req.getContentType())) {
            throw new AppException(ERROR_TYPE_FILE);
        }
        if (req.getSize() > MAX_FILE_SIZE) {
            throw new AppException(ERROR_LIMIT_SIZE_FILE);
        }
    }
}
