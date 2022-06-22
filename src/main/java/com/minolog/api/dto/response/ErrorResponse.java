package com.minolog.api.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다."
 *     "validation": {
 *         "title": "값을 입력해 주세요",
 *     }
 * }
 */
@RequiredArgsConstructor
@Getter
public class ErrorResponse {
    private final int code;
    private final String message;
    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(int code, String message, List<FieldError> validation) {
        this.code = code;
        this.message = message;
        this.validation = addValidation(validation);
    }

    public Map<String, String> addValidation(List<FieldError> validation) {
        return validation.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
