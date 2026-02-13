package ir.maleki.sideprojects.nexttick.application.reservation;

import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotInfo;
import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotPriority;
import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotPriorityQueue;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class QueueInitializer {
    private final JpaTimeSlotRepository repository;
    private final TimeSlotPriorityQueue timeSlotPriorityQueue;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeQueue() {
        List<TimeSlotPriority> items = repository.findAllByReservedFalseAndStartTimeAfter(LocalDateTime.now())
                .stream()
                .map(slot -> new TimeSlotPriority(
                        new TimeSlotInfo(slot.id()),
                        slot.startTime().atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
                ))
                .collect(Collectors.toList());

        timeSlotPriorityQueue.rebuild(items);
    }
}
