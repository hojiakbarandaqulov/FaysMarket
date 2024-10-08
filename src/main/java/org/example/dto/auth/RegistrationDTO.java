package org.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationDTO {
    @NotBlank(message = "email required")
    private String phone;
    @NotBlank(message = "password required")
    private String password;
}
