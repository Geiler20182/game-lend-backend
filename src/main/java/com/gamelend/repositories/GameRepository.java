package com.gamelend.repositories;

import com.gamelend.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByNameContainingIgnoreCase(String name);
    List<Game> findByProducerContainingIgnoreCase(String producer);
    List<Game> findByDirectorContainingIgnoreCase(String director);
    List<Game> findByYear(int year);
    List<Game> findByPlatform(Game.Platform platform);
    List<Game> findByYearGreaterThanEqual(int year);
    List<Game> findByYearLessThanEqual(int year);
    List<Game> findByYearBetween(int startYear, int endYear);

    @Query("SELECT r.gameId, COUNT(r.gameId) as count FROM Rental r GROUP BY r.gameId ORDER BY count DESC")
    List<Object[]> findMostRentedGameId();



}
