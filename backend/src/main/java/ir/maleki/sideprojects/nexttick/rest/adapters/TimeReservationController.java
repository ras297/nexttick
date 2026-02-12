package ir.maleki.sideprojects.nexttick.rest.adapters;

import ir.maleki.sideprojects.nexttick.application.reservation.ReserveTimeSlot;
import ir.maleki.sideprojects.nexttick.application.reservation.TimeReservationService;
import ir.maleki.sideprojects.nexttick.domain.TimeSlot;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<TimeSlotDto> reserveTimeSlot(@RequestBody @Validated ReserveTimeSlot request) {
        TimeSlot timeSlot = timeReservationService.reserveTimeSlot(request);
        if (timeSlot == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(timeSlot.id())
                .toUri();
        return ResponseEntity.created(location).body(new TimeSlotDto(timeSlot.id(), timeSlot.holderId()));
    }
}