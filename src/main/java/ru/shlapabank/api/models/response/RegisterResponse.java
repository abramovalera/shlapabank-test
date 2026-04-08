package ru.shlapabank.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель ответа регистрации.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Integer id;
    private String login;
    private String email;
    private String role;
    private String status;
    private String first_name;
    private String last_name;
    private String phone;
}
