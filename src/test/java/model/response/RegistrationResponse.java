package model.response;

import enums.Role;
import enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {

    private long id;
    private String login;
    private String email;
    private Role role;
    private Status status;
    private String first_name;
    private String last_name;
    private String phone;
}
