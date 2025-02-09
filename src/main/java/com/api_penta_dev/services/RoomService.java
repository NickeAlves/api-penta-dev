package com.api_penta_dev.services;

import com.api_penta_dev.dto.RoomDTO;
import com.api_penta_dev.models.GameStatus;
import com.api_penta_dev.models.Room;
import com.api_penta_dev.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> findById(String id) {
        return roomRepository.findById(id);
    }

    public List<Room> findByOwnerId(String ownerId) {
        return roomRepository.findByOwnerId(ownerId);
    }

    public List<Room> findByGameStatus(GameStatus gameStatus) {
        return roomRepository.findByGameStatus(gameStatus);
    }

    public Room createRoom(String name, Integer players, GameStatus gameStatus, String ownerId) {
        Room room = new Room(name, players, gameStatus, ownerId);
        return roomRepository.save(room);
    }

    public Room createRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setName(roomDTO.name());
        room.setPlayers(roomDTO.players() != null ? roomDTO.players() : 0);
        room.setGameStatus(roomDTO.gameStatus() != null ? roomDTO.gameStatus() : GameStatus.WAITING);
        room.setOwnerId(roomDTO.ownerId());

        return roomRepository.save(room);
    }

    public void deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);
    }

    public Room updateRoom(String roomId, RoomDTO roomDTO) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);

        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();

            room.setName(roomDTO.name() != null ? roomDTO.name() : room.getName());
            room.setPlayers(roomDTO.players() != null ? roomDTO.players() : room.getPlayers());
            room.setGameStatus(roomDTO.gameStatus() != null ? roomDTO.gameStatus() : room.getGameStatus());
            room.setOwnerId(roomDTO.ownerId() != null ? roomDTO.ownerId() : room.getOwnerId());

            return roomRepository.save(room);
        } else {
            throw new IllegalArgumentException("Room not found with ID: " + roomId);
        }
    }
}