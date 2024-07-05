package org.example.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ContractCreateDTO {
    private String productName;
    private String name;
    private String surname;
    private String scarf;
    private Double productPrice;
    private Double monthlyPayment;
    private String phone;
    private Double theRestIndebtedness;
    private LocalDate paymentDate=LocalDate.now();
}
