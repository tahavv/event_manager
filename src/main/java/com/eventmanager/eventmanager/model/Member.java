package com.eventmanager.eventmanager.model;

import com.eventmanager.eventmanager.model.enmus.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("MEMBER")
public class Member extends User {

    // If we want to store a separate many-to-many "member_events" table, define references carefully.
    @ManyToMany
    @JoinTable(
            name = "member_events",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> eventList;
}

