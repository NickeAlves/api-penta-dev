package com.api_penta_dev.models;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rooms")
public class Room {

    @Id
    private String id;

    private String name;

    private Integer players = 0;

    private GameStatus gameStatus = GameStatus.WAITING;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Room() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Room(String name, Integer players, GameStatus gameStatus) {
        this.name = name;
        this.players = players;
        this.gameStatus = gameStatus;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlayers() {
        return players;
    }

    public void setPlayers(Integer players) {
        this.players = players;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
