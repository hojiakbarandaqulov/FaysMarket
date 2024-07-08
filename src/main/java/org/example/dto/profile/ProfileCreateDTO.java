package org.example.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileCreateDTO {
    @NotBlank(message = "phone required")
    @Size(min = 9, max = 13, message = "min=9 and  max=13")
    private String phone;
    @NotBlank(message = "password required")
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
}
