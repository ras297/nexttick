package ir.maleki.sideprojects.nexttick.rest.adapters;

import java.io.Serializable;

public record TimeSlotDto(Long id, Long userId) implements Serializable {
}