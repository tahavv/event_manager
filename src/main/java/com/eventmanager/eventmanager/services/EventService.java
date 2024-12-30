package com.eventmanager.eventmanager.services;

import com.eventmanager.eventmanager.dto.EventDTO;
import com.eventmanager.eventmanager.model.Event;
import com.eventmanager.eventmanager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Create an Event
    public Event createEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setCategory(eventDTO.getCategory());
        event.setDateDebut(eventDTO.getDateDebut());
        event.setDateFin(eventDTO.getDateFin());
        event.setLocation(eventDTO.getLocation());
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
