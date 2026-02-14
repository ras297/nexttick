package ir.maleki.sideprojects.nexttick.rest.adapters;

import ir.maleki.sideprojects.nexttick.application.reservation.ReserveNextTimeSlot;
import ir.maleki.sideprojects.nexttick.application.reservation.TimeReservationService;
import ir.maleki.sideprojects.nexttick.domain.TimeSlot;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class TimeReservationController {
    private final TimeReservationService timeReservationService;

    @PostMapping
    public ResponseEntity<TimeSlotDto> reserveTimeSlot(@RequestBody @Validated ReserveNextTimeSlot request) {
        TimeSlot timeSlot = timeReservationService.reserveNextTimeSlot(request);
        if (timeSlot == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(timeSlot.id())
                .toUri();
        return ResponseEntity.created(location).body(new TimeSlotDto(timeSlot.id(), timeSlot.holderId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        timeReservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}