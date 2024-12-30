package com.eventmanager.eventmanager.services;

import com.eventmanager.eventmanager.dto.EventDTO;
import com.eventmanager.eventmanager.dto.RoomDTO;
import com.eventmanager.eventmanager.model.Event;
import com.eventmanager.eventmanager.model.Room;
import com.eventmanager.eventmanager.model.User;
import com.eventmanager.eventmanager.repository.EventRepository;
import com.eventmanager.eventmanager.repository.RoomRepository;
import com.eventmanager.eventmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;

    // Create an Event
    public Event createEvent(EventDTO eventDTO) {
        Event event = new Event();
        User organizer = userRepository.findById(eventDTO.getOrganizerId())
                .orElseThrow(() -> new IllegalArgumentException("Organizer not found"));
        Room room = roomRepository.findById(eventDTO.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setCategory(eventDTO.getCategory());
        event.setDateDebut(eventDTO.getDateDebut());
        event.setDateFin(eventDTO.getDateFin());
        event.setLocation(eventDTO.getLocation());
        event.setOrganizer(organizer);
        event.setRoom(room);

        return eventRepository.save(event);
    }

    // Update an Event
    public Optional<Event> updateEvent(Long id, EventDTO eventDTO) {
        return eventRepository.findById(id).map(event -> {
            event.setName(eventDTO.getName());
            event.setDescription(eventDTO.getDescription());
            event.setCategory(eventDTO.getCategory());
            event.setDateDebut(eventDTO.getDateDebut());
            event.setDateFin(eventDTO.getDateFin());
            event.setLocation(eventDTO.getLocation());
            return eventRepository.save(event);
        });
    }

    // Get all Events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Get Event by ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Delete an Event
    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
