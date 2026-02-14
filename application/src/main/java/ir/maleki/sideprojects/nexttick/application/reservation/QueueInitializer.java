package ir.maleki.sideprojects.nexttick.application.reservation;

import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotInfo;
import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotPriority;
import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotPriorityQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Component
public class QueueInitializer {
    private final JpaTimeSlotRepository repository;
    private final TimeSlotPriorityQueue timeSlotPriorityQueue;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeQueue() {
        log.info("Starting TimeSlotPriorityQueue rebuild...");
        long start = System.currentTimeMillis();

        List<TimeSlotPriority> items = repository.findAllByReservedFalseAndStartTimeAfter(LocalDateTime.now())
                .stream()
                .map(slot -> new TimeSlotPriority(
                        new TimeSlotInfo(slot.id()),
                        slot.startTimeEpochMilli()
                ))
                .collect(Collectors.toList());

        timeSlotPriorityQueue.rebuild(items);

        long duration = System.currentTimeMillis() - start;

        log.info("Queue rebuild completed: {} slots loaded in {} ms",
                items.size(), duration);
    }
}
