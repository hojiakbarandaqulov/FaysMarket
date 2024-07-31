package org.example.dto.contract;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ContractCreateDTO {
    private String name;
    private String surname;
    private String scarf;
    private LocalDate contractLifeTime=LocalDate.now();
    private String productName;
    private Double productPrice;
    private Double monthlyPayment;
    private String phone;

}
