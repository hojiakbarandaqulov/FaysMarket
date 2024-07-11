package org.example.dto.contract;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ContractResponseDTO {
    private String productName;
    private String name;
    private String surname;
    private String scarf;
    private LocalDate contractLifeTime=LocalDate.now();
    private Double productPrice;
    private Double monthlyPayment;
    private String phone;
    private LocalDateTime contractSignedTime;
}
