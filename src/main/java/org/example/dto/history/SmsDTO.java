package org.example.dto.history;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class SmsDTO {
    private Integer id;
    @NotBlank(message = "phone required")
    private String phone;
    @NotBlank(message = "message required")
    private String message;
    @NotBlank(message = "createdDate required")
    private LocalDateTime createdDate;
}
