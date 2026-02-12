package ir.maleki.sideprojects.nexttick.application.reservation;

import ir.maleki.sideprojects.nexttick.domain.TimeSlot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TimeReservationService {
    private final JpaTimeSlotRepository repository;

    public TimeReservationService(JpaTimeSlotRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TimeSlot reserveTimeSlot(ReserveTimeSlot command) {
        if (command.holderId() == null) {
            throw new IllegalStateException("User id is null");
        }
        return repository.reserveNextAvailableSlot(command.holderId());
    }

}
