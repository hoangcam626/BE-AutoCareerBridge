package com.backend.autocarrerbridge.dto.image.sdo;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ImageSdo {
    private Resource resource;
    private String mediaType;
}
