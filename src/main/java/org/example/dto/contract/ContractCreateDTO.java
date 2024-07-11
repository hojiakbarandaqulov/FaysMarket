package org.example.dto.contract;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ContractCreateDTO {
    private String name;
    private String surname;
    private String scarf;
    private String productName;
    private LocalDate contractLifeTime;
    private Double productPrice;
    private Double monthlyPayment;
    private String phone;

}
