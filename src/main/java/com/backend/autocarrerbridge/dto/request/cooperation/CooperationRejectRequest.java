package com.backend.autocarrerbridge.dto.request.cooperation;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static com.backend.autocarrerbridge.util.Constant.CONTENT_TOO_LONG_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.TEXT_REGEX;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CooperationRejectRequest {
    Integer idCooperation;

    @Pattern(regexp = TEXT_REGEX, message = CONTENT_TOO_LONG_MESSAGE)
    String message;
}
