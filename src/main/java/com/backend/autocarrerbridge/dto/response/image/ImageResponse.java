package com.backend.autocarrerbridge.dto.response.image;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ImageResponse {
    private Resource resource;
    private String mediaType;
}
