package ir.maleki.sideprojects.nexttick.application.reservation;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record ReserveTimeSlot(@NotNull Long holderId) implements Serializable {
}