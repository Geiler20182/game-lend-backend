package com.gamelend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamelend.entities.Game;
import com.gamelend.repositories.GameRepository;
import com.gamelend.repositories.RentalRepository;
import com.gamelend.servicies.GameService;
import com.gamelend.servicies.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/games")
@AllArgsConstructor
public class GameController {

    private final GameService gameService;
    private final RentalRepository rentalRepository;
    private final GameRepository gameRepository;
    private final RentalService rentalService;

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        try {
            Game createdGame = gameService.createGame(game);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGame);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        Optional<Game> game = gameService.getGameById(id);
        return game.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game game) {
        Optional<Game> updatedGame = gameService.updateGame(id, game);
        return updatedGame.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable Long id) {
        Optional<Game> deletedGame = gameService.deleteGame(id);
        return deletedGame.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/search")
    public ResponseEntity<List<Game>> searchGames(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String producer,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer startYear,
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) Game.Platform platform) {

        if (name != null) {
            return ResponseEntity.ok(gameService.searchByName(name));
        } else if (producer != null) {
            return ResponseEntity.ok(gameService.searchByProducer(producer));
        } else if (director != null) {
            return ResponseEntity.ok(gameService.searchByDirector(director));
        } else if (year != null) {
            return ResponseEntity.ok(gameService.searchByYear(year));
        } else if (startYear != null && endYear != null) {
            return ResponseEntity.ok(gameService.searchByYearRange(startYear, endYear));
        } else if (platform != null) {
            return ResponseEntity.ok(gameService.searchByPlatform(platform));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/most-rented")
    public ResponseEntity<?> getMostRentedGame() {
        Long gameId = gameService.getMostRentedGameId();
        if (gameId == null) {
            return ResponseEntity.ok().body("{\"not found\":1}");
        } else {
            Game game = gameService.getGameById(gameId).orElse(null);
            if (game != null) {
                return ResponseEntity.ok().body(game);
            } else {
                return ResponseEntity.ok().build();
            }
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Game> updateGameStatus(@PathVariable Long id, @RequestParam Game.GameStatus status) {
        Optional<Game> updatedGame = gameService.updateGameStatus(id, status);
        return updatedGame.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }




}
