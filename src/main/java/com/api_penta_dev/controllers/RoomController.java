package com.api_penta_dev.controllers;


import com.api_penta_dev.models.Room;
import com.api_penta_dev.repositories.RoomRepository;
import com.api_penta_dev.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.findAll();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable String id) {
        Optional<Room> roomOpt = roomService.findById(id);
        if (roomOpt.isPresent()) {
            return ResponseEntity.ok(roomOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room savedRoom = roomService.createRoom(room);
        return ResponseEntity.status(201).body(savedRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>  updateRoom(@PathVariable String id, @RequestBody Room roomDetails) {
        Optional<Room> roomOpt = roomService.findById(id);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            room.setName(roomDetails.getName());
            room.setPlayers(roomDetails.getPlayers());
            room.setGameStatus(roomDetails.getGameStatus());
            roomService.updateRoom(id,roomDetails);
            return ResponseEntity.ok().body("Room updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable String id) {
        Optional<Room> roomOpt = roomService.findById(id);
        if (roomOpt.isPresent()) {
            roomService.deleteById(id);
            return ResponseEntity.ok().body("Room deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

