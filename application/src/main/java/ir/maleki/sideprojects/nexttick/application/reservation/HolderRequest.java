package ir.maleki.sideprojects.nexttick.application.reservation;

import jakarta.validation.constraints.NotNull;

public record HolderRequest(@NotNull Long holderId) {}