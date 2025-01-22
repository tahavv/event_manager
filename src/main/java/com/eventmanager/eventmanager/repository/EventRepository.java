package com.eventmanager.eventmanager.repository;

import com.eventmanager.eventmanager.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("""
   SELECT e
   FROM Event e
   WHERE e.room.id = :roomId
     AND (
       (e.dateDebut < :end AND e.dateFin > :start)
     )
""")
    List<Event> findOverlappingEvents(@Param("roomId") Long roomId,
                                      @Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end);
}
