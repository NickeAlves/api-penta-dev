package com.api_penta_dev.services;

import com.api_penta_dev.models.GameStatus;
import com.api_penta_dev.models.Room;
import com.api_penta_dev.repositories.RoomRepository;
import com.api_penta_dev.repositories.RoomRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Optional<Room> updateRoom(String id, Room room) {
        return roomRepository.findById(id).map(existingRoom -> {
            existingRoom.setName(room.getName());
            existingRoom.setPlayers(room.getPlayers());
            existingRoom.setGameStatus(room.getGameStatus());
            return roomRepository.save(existingRoom);
        });
    }

    public boolean deleteById(String id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
