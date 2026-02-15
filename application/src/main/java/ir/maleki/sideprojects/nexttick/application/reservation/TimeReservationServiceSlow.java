package ir.maleki.sideprojects.nexttick.application.reservation;

import ir.maleki.sideprojects.nexttick.domain.TimeSlot;
import ir.maleki.sideprojects.nexttick.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TimeReservationServiceSlow {
    private final JpaTimeSlotRepository repository;

    @Transactional
    public TimeSlot reserveNextTimeSlot(ReserveNextTimeSlot command) {
        if (command.holderId() == null) {
            throw new IllegalStateException("Holder id is null");
        }
        repository.findById(command.holderId()).orElseThrow(IllegalArgumentException::new);

        return repository.reserveNextAvailableSlot(command.holderId());
    }

}