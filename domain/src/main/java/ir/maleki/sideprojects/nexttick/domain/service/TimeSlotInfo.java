package ir.maleki.sideprojects.nexttick.domain.service;

public record TimeSlotInfo(Long id) {
    public TimeSlotInfo {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
    }
}
