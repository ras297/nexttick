package ir.maleki.sideprojects.nexttick.application.reservation;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record ReserveNextTimeSlot(@NotNull Long holderId) implements Serializable {
}