package com.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "firstname must not be blank")
    private String firstName;

    @NotBlank(message = "lastname must not be blank")
    private String lastName;

    @NotBlank(message = "email must not be blank")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "phone number must not be blank")
    @Column(unique = true)
    private String phoneNumber;

    @NotBlank(message = "address must not be blank")
    private String address;


    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Salary> salaries;

}
