package com.gamelend.entities;

import jakarta.persistence.*;
import lombok.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "rental")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rental_id")
    @Getter @Setter
    private Long rentalId;

    @Column(name = "rental_date", nullable = false)
    @NotNull
    @Getter @Setter
    private Date rentalDate;

    @Column(name = "limit_date", nullable = false)
    @Getter @Setter
    private Date limitDate;

    @Column(name = "return_date")
    @Getter @Setter
    private Date returnDate;

    @Column(name = "customer_id", nullable = false)
    @NotNull
    @Getter @Setter
    private Long customerId;

    @Column(name = "game_id", nullable = false)
    @NotNull
    @Getter @Setter
    private Long gameId;

    @Column(name = "price")
    @Getter @Setter
    private Double rentalPrice;

}
