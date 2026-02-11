package ir.maleki.sideprojects.nexttick.application.reservation;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record ReserveTimeSlot(@NotBlank Long userId) implements Serializable {
}