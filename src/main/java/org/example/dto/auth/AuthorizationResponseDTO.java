package org.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.enums.ProfileRole;

@Data
public class AuthorizationResponseDTO {
    @NotNull
    private Integer id;
    @NotBlank(message = "role required")
    private ProfileRole role;
    private String jwt;
}
