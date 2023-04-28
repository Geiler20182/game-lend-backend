package com.gamelend.servicies;

import com.gamelend.entities.Customer;
import com.gamelend.entities.Game;
import com.gamelend.entities.Rental;
import com.gamelend.repositories.GameRepository;
import com.gamelend.repositories.RentalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class RentalService {


    private final RentalRepository rentalRepository;
    private final GameRepository gameRepository;


    @Transactional
    public Rental createRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    @Transactional
    public Optional<Rental> updateRental(Long id, Rental rental) {
        Optional<Rental> existingRental = rentalRepository.findById(id);
        if (existingRental.isPresent()) {
            rental.setRentalId(id);
            return Optional.of(rentalRepository.save(rental));
        }

        return Optional.empty();
    }

    @Transactional
    public Optional<Rental> deleteRental(Long id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        rental.ifPresent(rentalRepository::delete);
        return rental;
    }

    @Transactional
    public Long getMostFrequentCustomerId() {
        return rentalRepository.findMostFrequentCustomerId();
    }

    public List<Rental> getSalesByDate(Date date) {
        return rentalRepository.findByRentalDate(date);
    }


    private String getAgeRangeKey(int age) {
        int startAge = (age / 10) * 10;
        int endAge = startAge + 10;
        return String.format("%d/%d", startAge, endAge);
    }






}
