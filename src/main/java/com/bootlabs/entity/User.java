package com.bootlabs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    // Simple string concatenation
    @Formula("first_name || ' ' || last_name")
    private String fullName;

    // age calculated from birth date
    @Formula("YEAR(CURDATE()) - YEAR(birth_date)")
    private Integer age;

    // Basic mathematical operation
    @Column(name = "salary")
    private BigDecimal salary;

    // 10% of salary as tax
    @Formula("salary * 0.10")
    private BigDecimal taxAmount;
}