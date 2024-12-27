package com.backend.autocarrerbridge.converter;

import com.backend.autocarrerbridge.dto.response.cooperation.SentRequestResponse;
import com.backend.autocarrerbridge.entity.BusinessUniversity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SentRequestConverter {

    /**
     * Chuyển đổi đối tượng BusinessUniversity thành SentRequestResponse
     */
    public List<SentRequestResponse> toSentRequestResponse(List<BusinessUniversity> businessUniversities) {
        if (businessUniversities == null || businessUniversities.isEmpty()) {
            return List.of(); // Trả về danh sách rỗng nếu đầu vào null hoặc trống
        }
        return businessUniversities.stream()
                .map(SentRequestResponse::new) // Tạo SentRequestResponse từ từng BusinessUniversity
                .collect(Collectors.toList());
    }
}
