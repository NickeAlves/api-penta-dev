package com.api_penta_dev.repositories;

import com.api_penta_dev.models.GameStatus;
import com.api_penta_dev.models.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByOwnerId(String ownerId);
    List<Room> findByGameStatus(GameStatus gameStatus);
}
