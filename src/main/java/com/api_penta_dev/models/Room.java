package com.api_penta_dev.models;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rooms")
public class Room {

    @Id
    private String id;

    @NotEmpty(message = "Room name cannot be empty")
    private String name;

    private Integer players = 0;

    @NotNull(message = "Game status cannot be null")
    private GameStatus gameStatus = GameStatus.WAITING;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NotEmpty(message = "Owner ID cannot be empty")
    private String ownerId;

    public Room() {
    }

    public Room(String name, Integer players, GameStatus gameStatus, String ownerId) {
        this.name = name;
        this.players = players;
        this.gameStatus = gameStatus;
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}