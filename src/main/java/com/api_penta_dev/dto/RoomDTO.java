package com.api_penta_dev.dto;

import com.api_penta_dev.models.GameStatus;

public record RoomDTO(String name, Integer players, GameStatus gameStatus, String ownerId) {
}
