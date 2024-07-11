package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    private Boolean visible;
    private LocalDateTime createdDate;
    private Integer photoId;
    private String jwt;

}
