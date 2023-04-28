package com.gamelend.servicies;

import com.gamelend.entities.Game;
import com.gamelend.entities.Rental;
import com.gamelend.repositories.GameRepository;
import com.gamelend.repositories.RentalRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RentalServiceTest {

    @Autowired
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private GameRepository gameRepository;

    @Test
    void testGetSalesByDate() {

        // Crear un objeto java.sql.Date a partir de un objeto LocalDate
        LocalDate rentalDate = LocalDate.of(2022, 3, 1);
        java.sql.Date sqlRentalDate = java.sql.Date.valueOf(rentalDate);

        // Crear un objeto java.sql.Timestamp a partir de un objeto LocalDateTime
        LocalDateTime limitDate = LocalDateTime.of(2022, 4, 1, 11, 25, 5);
        java.sql.Timestamp sqlLimitDate = java.sql.Timestamp.valueOf(limitDate);

        // Crear un objeto java.sql.Timestamp a partir de un objeto LocalDateTime con zona horaria UTC
        LocalDateTime returnDate = LocalDateTime.of(2022, 4, 15, 12, 0, 0);

        ZonedDateTime utcReturnDate = ZonedDateTime.of(returnDate, ZoneOffset.UTC);
        java.sql.Timestamp sqlUtcReturnDate = java.sql.Timestamp.valueOf(utcReturnDate.toLocalDateTime());

        Rental rental1 = new Rental(1L, sqlRentalDate, sqlLimitDate, sqlUtcReturnDate, 53L, 302L, 10.0);
        Rental rental2 = new Rental(2L, sqlRentalDate, sqlLimitDate, sqlUtcReturnDate, 53L, 302L, 10.0);
        Rental rental3 = new Rental(3L, sqlRentalDate, sqlLimitDate, sqlUtcReturnDate, 53L, 302L, 10.0);

        rentalRepository.saveAll(Arrays.asList(rental1, rental2, rental3));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 3, 1); // El mes comienza en 0, por lo que abril es el mes 3.
        List<Rental> result = rentalService.getSalesByDate(calendar.getTime());

        assertEquals(2, result.size());
    }





}
