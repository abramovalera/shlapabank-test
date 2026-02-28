package model.response.registration;

import enums.Role;
import enums.Status;
import lombok.Data;

@Data
public class RegistrationResponse {

    public long id;
    public String login;
    public String email;
    public Role role;
    public Status status;
    public String first_name;
    public String last_name;
    public String phone;
}

