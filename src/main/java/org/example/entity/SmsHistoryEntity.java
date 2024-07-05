package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", columnDefinition = "text")
    private String code;

    @Column(name = "phone")
    @Size(min = 9,max = 13)
    private String phone;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
