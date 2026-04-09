package ru.shlapabank.api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
/**
 * Запрос на частичное обновление профиля пользователя.
 * В JSON попадают только переданные поля, null-поля исключаются.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileUpdateRequest {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String phone;
    private String email;
    @JsonProperty("current_password")
    private String currentPassword;
    @JsonProperty("new_password")
    private String newPassword;
}
