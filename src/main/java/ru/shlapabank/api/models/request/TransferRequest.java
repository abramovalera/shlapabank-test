package ru.shlapabank.api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * Запрос на перевод между счетами.
 * Null-поля исключаются из JSON (например, когда OTP не требуется в сценарии).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferRequest {
    @JsonProperty("from_account_id")
    private Long fromAccountId;
    @JsonProperty("to_account_id")
    private Long toAccountId;
    private BigDecimal amount;
    @JsonProperty("otp_code")
    private String otpCode;
}
