package com.gamelend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.gamelend.servicies.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.gamelend.entities.Game;
import com.gamelend.repositories.GameRepository;

@SpringBootTest
class GameServiceTests {

    private GameService gameService;

    @Mock
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService = new GameService(gameRepository, null, null);
    }

    @Test
    void testCreateGame() {
        Game game = new Game();
        game.setName("Test Game");
        game.setYear(2022);
        game.setDirector("John Doe");
        game.setProducer("Jane Smith");
        game.setPlatform(Game.Platform.PC);

        when(gameRepository.save(game)).thenReturn(game);

        Game result = gameService.createGame(game);
        assertEquals(game, result);
    }

    @Test
    void testGetGameById() {
        Game game = new Game();
        game.setGameId(1L);
        game.setName("Test Game");
        game.setYear(2022);
        game.setDirector("John Doe");
        game.setProducer("Jane Smith");
        game.setPlatform(Game.Platform.PC);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        Optional<Game> result = gameService.getGameById(1L);
        assertTrue(result.isPresent());
        assertEquals(game, result.get());
    }

    @Test
    void testGetAllGames() {
        Game game1 = new Game();
        game1.setGameId(1L);
        game1.setName("Test Game 1");
        game1.setYear(2022);
        game1.setDirector("John Doe");
        game1.setProducer("Jane Smith");
        game1.setPlatform(Game.Platform.PC);

        Game game2 = new Game();
        game2.setGameId(2L);
        game2.setName("Test Game 2");
        game2.setYear(2022);
        game2.setDirector("John Doe");
        game2.setProducer("Jane Smith");
        game2.setPlatform(Game.Platform.PC);

        List<Game> games = Arrays.asList(game1, game2);

        when(gameRepository.findAll()).thenReturn(games);

        List<Game> result = gameService.getAllGames();
        assertEquals(games, result);
    }

    @Test
    void testUpdateGame() {
        Game existingGame = new Game();
        existingGame.setGameId(1L);
        existingGame.setName("Test Game");
        existingGame.setYear(2022);
        existingGame.setDirector("John Doe");
        existingGame.setProducer("Jane Smith");
        existingGame.setPlatform(Game.Platform.PC);

        Game updatedGame = new Game();
        updatedGame.setGameId(1L);
        updatedGame.setName("Test Game 2");
        updatedGame.setYear(2023);
        updatedGame.setDirector("John Doe Jr");
        updatedGame.setProducer("Jane Smith Jr");
        updatedGame.setPlatform(Game.Platform.PLAY_STATION);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(gameRepository.save(updatedGame)).thenReturn(updatedGame);

        Optional<Game> result = gameService.updateGame(1L, updatedGame);
        assertTrue(result.isPresent());
        assertEquals(updatedGame, result.get());
    }

}
