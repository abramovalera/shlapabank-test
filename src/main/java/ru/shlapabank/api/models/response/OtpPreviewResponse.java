package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель ответа helper-эндпоинта предпросмотра OTP.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class OtpPreviewResponse {
    private Long userId;
    private String otp;
    private Integer ttlSeconds;
    private String message;
}
