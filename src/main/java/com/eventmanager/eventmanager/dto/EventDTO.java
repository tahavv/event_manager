package com.eventmanager.eventmanager.dto;

import com.eventmanager.eventmanager.model.enmus.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
@Schema(description = "Data Transfer Object for creating and updating events")
@Data
public class EventDTO {
    private String name;
    private String description;
    private Category category;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String location;
    private Long organizerId;
    private Long roomId;
}
