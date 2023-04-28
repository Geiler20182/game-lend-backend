package com.gamelend.controllers;


import com.gamelend.entities.Game;
import com.gamelend.entities.Rental;
import com.gamelend.servicies.GameService;
import com.gamelend.servicies.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
@AllArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    private final GameService gameService;
    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        try {
            Long gameId = rental.getGameId();
            Optional<Game> game = gameService.getGameById(gameId);
            if (game.isPresent() && game.get().getGameStatus() == Game.GameStatus.AVAILABLE) {
                Rental  createdRental = rentalService.createRental(rental);
                game.get().setGameStatus(Game.GameStatus.UNAVAILABLE);
                gameService.updateGame(gameId, game.get());
                return ResponseEntity.status(HttpStatus.CREATED).body(createdRental);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @RequestBody Rental rental) {

        try {

            Optional<Rental> updatedRental = rentalService.updateRental(id, rental);
            if (updatedRental.isPresent()) {
                Long gameId = updatedRental.get().getGameId();
                Optional<Game> game = gameService.getGameById(gameId);
                if (game.isPresent()) {
                    gameService.updateGame(gameId, game.get());
                }
                return ResponseEntity.ok(updatedRental.get());

            }
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Rental> deleteRental(@PathVariable Long id) {
        Optional<Rental> deletedRental = rentalService.deleteRental(id);
        return deletedRental.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sales")
    public ResponseEntity<List<Rental>> getSalesByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        try {

            List<Rental> sales = rentalService.getSalesByDate(date);
            return ResponseEntity.ok(sales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
