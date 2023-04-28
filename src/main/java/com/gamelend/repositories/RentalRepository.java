package com.gamelend.repositories;

import com.gamelend.entities.Customer;
import com.gamelend.entities.Game;
import com.gamelend.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query(value = "SELECT game_id FROM rental GROUP BY game_id ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    Long findMostRentedGameId();

    @Query(value = "SELECT c.customer_id FROM rental r INNER JOIN customer c ON " +
                   "r.customer_id = c.customer_id GROUP BY r.customer_id, " +
                    "c.customer_id ORDER BY COUNT(*) DESC LIMIT 1", nativeQuery = true)
    Long findMostFrequentCustomerId();

    @Query(value = "SELECT r FROM Rental r WHERE r.rentalDate = :date")
    List<Rental> findByRentalDate(@Param("date") Date date);




}
