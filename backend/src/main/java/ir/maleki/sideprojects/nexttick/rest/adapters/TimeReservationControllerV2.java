package ir.maleki.sideprojects.nexttick.rest.adapters;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.maleki.sideprojects.nexttick.application.reservation.ReserveNextTimeSlot;
import ir.maleki.sideprojects.nexttick.application.reservation.TimeReservationServiceSlow;
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
@RequestMapping("/api/v2/reservations")
@Tag(name = "Time Slot API (Mock, does not call services)", description = "Operations related to time slot reservation")
public class TimeReservationControllerV2 {
    private final TimeReservationServiceSlow timeReservationService;

    @Operation(summary = "Reserve Time Slot Using old non-optimized policy", description = "Reserve the nearest available time slot for the user")
    @PostMapping
    public ResponseEntity<TimeSlotDto> reserveTimeSlot(@RequestBody @Validated ReserveNextTimeSlot request) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(1L)
                .toUri();
        return ResponseEntity.created(location).body(new TimeSlotDto(1L, request.holderId()));
    }
}