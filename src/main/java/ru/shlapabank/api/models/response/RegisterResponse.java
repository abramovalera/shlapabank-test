package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shlapabank.enums.UserRole;
import ru.shlapabank.enums.UserStatus;

/**
 * Модель ответа регистрации.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class RegisterResponse {
    private Integer id;
    private String login;
    private String email;
    private UserRole role;
    private UserStatus status;
    private String first_name;
    private String last_name;
    private String phone;
}
