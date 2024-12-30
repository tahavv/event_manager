package com.eventmanager.eventmanager.services;

import com.eventmanager.eventmanager.dto.RoomDTO;
import com.eventmanager.eventmanager.model.Room;
import com.eventmanager.eventmanager.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Create a Room
    public Room createRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setName(roomDTO.getName());
        room.setCapacity(roomDTO.getCapacity());
        return roomRepository.save(room);
    }


    // Get all Rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get Room by ID
    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    // Update a Room
    public Optional<Room> updateRoom(Long id, RoomDTO roomDTO) {
        return roomRepository.findById(id).map(room -> {
            if (roomDTO.getName() != null) {
                room.setName(roomDTO.getName());
            }
            if (roomDTO.getCapacity() > 0) {
                room.setCapacity(roomDTO.getCapacity());
            }
            return roomRepository.save(room);
        });
    }

    // Delete a Room
    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            roomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
