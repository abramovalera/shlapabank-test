package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String account_number;
    private AccountType account_type;
    private Currency currency;
    private BigDecimal balance;
    private Boolean is_primary;
}
