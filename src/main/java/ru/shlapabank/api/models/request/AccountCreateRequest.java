package ru.shlapabank.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;

/**
 * Модель запроса открытия счета.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {
    private AccountType account_type;
    private Currency currency;
}
