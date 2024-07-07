package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.service.converter.LocalDateTimeConverter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@Table(name = "contracts")
public class ContractsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "scarf")
    private String scarf;

    @Column(name = "contract_life_time")
    private LocalDate contractLifeTime=LocalDate.now();

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "monthly_payment")
    private Double monthlyPayment;

    @Column(name = "phone")
    private String phone;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime timeDayMonthYear;


}
