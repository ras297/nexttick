package ir.maleki.sideprojects.nexttick.application.reservation;

import ir.maleki.sideprojects.nexttick.domain.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaTimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findAllByReservedFalseAndStartTimeAfter(LocalDateTime time);
}
