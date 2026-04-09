package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;

import java.math.BigDecimal;

/**
 * Модель ответа открытия счета.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class AccountResponse {
    private Long id;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_type")
    private AccountType accountType;

    private Currency currency;
    private BigDecimal balance;

    @JsonProperty("is_primary")
    private Boolean isPrimary;
}
