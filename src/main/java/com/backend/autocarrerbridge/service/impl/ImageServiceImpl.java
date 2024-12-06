package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.autocarrerbridge.dto.response.image.ImageResponse;
import com.backend.autocarrerbridge.entity.Image;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.controller.repository.ImageRepository;
import com.backend.autocarrerbridge.service.ImageService;

import lombok.RequiredArgsConstructor;

/**
 * ImageServiceImpl là lớp triển khai cho ImageService, cung cấp các chức năng xử lý tệp hình ảnh.
 * Lớp sử dụng ImageRepository để lưu trữ và truy xuất thông tin hình ảnh từ cơ sở dữ liệu.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Value("${media.img_path}")
    private String imgFolder;

    private static final int BYTES_IN_KB = 1024;
    private static final int KB_IN_MB = 1024;
    private static final long MAX_FILE_SIZE_BYTES = 10L * BYTES_IN_KB * KB_IN_MB; // 10 MB

    private static final List<String> ACCEPTED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");

    /**
     * Lấy thông tin hình ảnh từ cơ sở dữ liệu theo ID.
     *
     * @param id - ID của hình ảnh.
     * @return Đối tượng Image chứa thông tin về hình ảnh.
     * @throws AppException nếu không tìm thấy hình ảnh.
     */
    public Image getImage(Integer id) {
        return imageRepository.findById(id).orElseThrow(() -> new AppException((ERROR_FIND_IMAGE)));
    }

    /**
     * Lấy đường dẫn tệp hình ảnh từ ID.
     *
     * @param id - ID của hình ảnh.
     * @return Đường dẫn tới tệp hình ảnh.
     */
    public String getPathImage(Integer id) {

        return imageRepository
                .findById(id)
                .map(image -> imgFolder + File.separator + image.getFilename())
                .orElse("");
    }

    /**
     * Tải lên một tệp hình ảnh.
     *
     * @param req - Tệp hình ảnh cần tải lên.
     * @return ID của hình ảnh vừa được lưu trữ.
     * @throws AppException nếu xảy ra lỗi khi lưu tệp.
     */
    public Integer uploadFile(MultipartFile req) {
        validateFile(req); // Kiểm tra file đầu vào

        try {
            createDirectoryIfNotExists(imgFolder); // Tạo thư mục lưu trữ nếu chưa tồn tại

            String fileName = String.format(
                    "%s%s",
                    UUID.randomUUID(), getFileExtension(req.getOriginalFilename())); // Tạo tên file không trùng lặp

            // Lưu file ảnh vào bộ nhớ
            Path filePath = Paths.get(imgFolder, fileName);
            try (OutputStream outputStream = new FileOutputStream(filePath.toFile())) {
                outputStream.write(req.getBytes());
            }

            // Lưu thông tin ảnh vào database
            Image image = imageRepository.save(Image.builder()
                    .filename(fileName)
                    .type(req.getContentType())
                    .build());
            return image.getId();
        } catch (IOException e) {
            throw new AppException(ERROR_SAVE_FILE);
        }
    }

    /**
     * Tải lên nhiều tệp hình ảnh.
     *
     * @param reqs - Danh sách các tệp hình ảnh cần tải lên.
     * @return Danh sách ID của các hình ảnh vừa được lưu trữ.
     */
    public List<Integer> uploadFiles(MultipartFile[] reqs) {
        return Arrays.stream(reqs).map(this::uploadFile).toList();
    }

    /**
     * Xóa hình ảnh khỏi hệ thống dựa trên ID.
     *
     * @param id - ID của hình ảnh cần xóa.
     * @throws AppException nếu xảy ra lỗi khi xóa hình ảnh hoặc không tìm thấy hình ảnh.
     */
    public void delete(Integer id) {

        Image image = getImage(id); // Tìm ảnh cần xóa trong database bằng id
        Path filePath = Paths.get(imgFolder, image.getFilename());
        try {
            Files.deleteIfExists(filePath); // Xóa file nếu tồn tại trong bộ nhớ
            imageRepository.deleteById(id); // Xóa thông itn ảnh trong database
        } catch (Exception e) {
            throw new AppException(ERROR_DELETE_IMAGE); // Bắn lỗi không xóa được ảnh
        }
    }

    /**
     * Lấy tài nguyên hình ảnh từ ID.
     *
     * @param id - ID của hình ảnh.
     * @return Đối tượng ImageResponse chứa tài nguyên và loại nội dung hình ảnh.
     * @throws IOException nếu xảy ra lỗi khi đọc tệp.
     */
    public ImageResponse getResource(Integer id) throws IOException {
        Path path = Paths.get(getPathImage(id));
        Image media = getImage(id); // Tìm kiếm aảnh trong database bằng id

        Resource resource = new UrlResource(path.toUri()); // Tạo đối tượng resource lấy ảnh

        if (resource.exists()) {
            return ImageResponse.of(resource, media.getType()); // Đầu ra chứa tài nguyên và loại nội dung hình ảnh.
        } else {
            throw new AppException(ERROR_FIND_IMAGE);
        }
    }

    /**
     * Lấy phần mở rộng của tệp từ tên tệp.
     *
     * @param fileName - Tên tệp.
     * @return Phần mở rộng của tệp (ví dụ: .jpg, .png).
     */
    public String getFileExtension(String fileName) {
        return "." + FilenameUtils.getExtension(fileName);
    }

    /**
     * Tạo thư mục nếu chưa tồn tại.
     *
     * @param folderPath - Đường dẫn thư mục cần tạo.
     * @throws AppException nếu không thể tạo thư mục.
     */
    private void createDirectoryIfNotExists(String folderPath) {
        File dir = new File(folderPath);
        if (!dir.exists()) {
            boolean checkCreated = dir.mkdirs();
            if (!checkCreated) {
                throw new AppException(ERROR_DIRECTORY_FILE);
            }
        }
    }

    /**
     * Xác thực tệp hình ảnh.
     *
     * @param req - Tệp hình ảnh cần xác thực.
     * @throws AppException nếu tệp không hợp lệ (rỗng, sai loại, vượt quá dung lượng).
     */
    private void validateFile(MultipartFile req) {
        if (req.isEmpty()) {
            throw new AppException(ERROR_EMPTY_FILE);
        }
        if (!ACCEPTED_CONTENT_TYPES.contains(req.getContentType())) {
            throw new AppException(ERROR_TYPE_FILE);
        }
        if (req.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new AppException(ERROR_LIMIT_SIZE_FILE);
        }
    }
}
