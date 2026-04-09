package ru.shlapabank.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopUpRequest {
    private BigDecimal amount;

    @JsonProperty("otp_code")
    private String otpCode;
}