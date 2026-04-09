package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String phone;
}
