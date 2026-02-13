package ir.maleki.sideprojects.nexttick.application.reservation;

import ir.maleki.sideprojects.nexttick.domain.TimeSlot;
import ir.maleki.sideprojects.nexttick.domain.User;
import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotInfo;
import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotPriorityQueue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TimeReservationService {
    private final JpaUserRepository userRepository;
    private final TimeSlotPriorityQueue timeSlotPriorityQueue;
    private final JpaTimeSlotRepository repository;

    @Transactional
    public TimeSlot reserveNextTimeSlot(ReserveNextTimeSlot command) {
        if (command.holderId() == null) {
            throw new IllegalStateException("Holder id is null");
        }
        User holdCandidate = userRepository.findById(command.holderId()).orElseThrow(IllegalArgumentException::new);

        TimeSlotInfo slotInfo = timeSlotPriorityQueue.poll();

        TimeSlot timeSlot = repository.findById(slotInfo.id()).orElseThrow(IllegalAccessError::new);
        timeSlot.reserveFor(holdCandidate);
        repository.save(timeSlot);

        return timeSlot;
    }

}
