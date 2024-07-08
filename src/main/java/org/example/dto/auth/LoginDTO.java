package org.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "phone required")
    private String phone;
    @NotBlank(message = "password required")
    private String password;
}
