package com.gamelend.servicies;

import com.gamelend.entities.Game;
import com.gamelend.repositories.CustomerRepository;
import com.gamelend.repositories.GameRepository;
import com.gamelend.repositories.RentalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;


    @Transactional
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Transactional
    public Optional<Game> updateGame(Long id, Game game) {
        Optional<Game> existingGame = gameRepository.findById(id);
        if (existingGame.isPresent()) {
            game.setGameId(id);
            return Optional.of(gameRepository.save(game));
        }
        return Optional.empty();
    }
    @Transactional
    public Optional<Game> updateGameStatus(Long id, Game.GameStatus status) {
        Optional<Game> existingGame = gameRepository.findById(id);
        if (existingGame.isPresent()) {
            existingGame.get().setGameStatus(status);
            return updateGame(id, existingGame.get());
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Game> deleteGame(Long id) {

        Optional<Game> game = gameRepository.findById(id);
        game.ifPresent(gameRepository::delete);
        return game;
    }

    public List<Game> searchByName(String name) {
        return gameRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Game> searchByProducer(String producer) {
        return gameRepository.findByProducerContainingIgnoreCase(producer);
    }

    public List<Game> searchByDirector(String director) {
        return gameRepository.findByDirectorContainingIgnoreCase(director);
    }

    public List<Game> searchByYear(int year) {
        return gameRepository.findByYear(year);
    }

    public List<Game> searchByPlatform(Game.Platform platform) {
        return gameRepository.findByPlatform(platform);
    }

    public List<Game> searchByYearRange(int startYear, int endYear) {
        return gameRepository.findByYearBetween(startYear, endYear);

    }

    @Transactional
    public Long getMostRentedGameId() {
        List<Object[]> result = gameRepository.findMostRentedGameId();
        if (result.isEmpty()) {
            return null; // o lanzar una excepción adecuada
        }
        return (Long) result.get(0)[0];
    }






}
