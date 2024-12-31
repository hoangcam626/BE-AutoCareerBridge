package com.backend.autocarrerbridge.dto.request.universityjob;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UniversityJobRequest {
    @NotNull(message = NO_CONTENT_MESSAGE)
    private Integer universityId;
    @NotNull(message = NO_CONTENT_MESSAGE)
    private Integer jobId;
}
