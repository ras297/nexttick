package ir.maleki.sideprojects.nexttick.application.reservation;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record CancelTimeSlot(@NotNull Long id, @NotNull Long holderId) implements Serializable {
}