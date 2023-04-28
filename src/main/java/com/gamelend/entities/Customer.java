package com.gamelend.entities;

import jakarta.persistence.*;
import lombok.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    @Getter @Setter
    private Long customerId;

    @Column(name = "name", nullable = false, length = 50)
    @NotNull @NotBlank
    @Getter @Setter
    private String name;

    @Column(name = "age")
    @Getter @Setter
    private int age;

    @Column(name = "address", nullable = false, length = 100)
    @NotNull @NotBlank
    @Getter @Setter
    private String address;

    @Column(name = "email", nullable = false, length = 50)
    @NotNull @NotBlank
    @Getter @Setter
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    @NotNull @NotBlank
    @Getter @Setter
    private String phone;

}
