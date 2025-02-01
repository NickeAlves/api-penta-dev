package com.api_penta_dev.repositories;

import com.api_penta_dev.models.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    // Additional query methods (if needed) can be defined here.
}
