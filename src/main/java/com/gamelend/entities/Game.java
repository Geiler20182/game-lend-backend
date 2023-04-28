package com.gamelend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    @Getter @Setter
    private Long gameId;

    @Column(name = "name", nullable = false, length = 50)
    @NotNull @NotBlank
    @Getter @Setter
    private String name;

    @Column(name = "year")
    @Getter @Setter
    private int year;

    @Column(name = "director", nullable = false, length = 50)
    @NotNull @NotBlank
    @Getter @Setter
    private String director;

    @Column(name = "producer", nullable = false, length = 50)
    @NotNull @NotBlank
    @Getter @Setter
    private String producer;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    @NotNull
    @Getter @Setter
    private Platform platform;

    @Column(name = "price")
    @Getter @Setter
    private Double price;

    @Column(name = "url_image")
    @Getter @Setter
    private String urlImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_status")
    @Getter @Setter
    private GameStatus gameStatus;

    public enum GameStatus {
        AVAILABLE,
        UNAVAILABLE
    }

    public enum Platform {
        XBOX,
        PLAY_STATION,
        PC,
        NINTENDO
    }


}
