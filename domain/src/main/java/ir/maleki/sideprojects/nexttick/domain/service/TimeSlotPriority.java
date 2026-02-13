package ir.maleki.sideprojects.nexttick.domain.service;

public record TimeSlotPriority(TimeSlotInfo timeSlotInfo, Long priority) {

    public TimeSlotPriority {
        if (timeSlotInfo == null) {
            throw new IllegalArgumentException("timeSlotId must not be null");
        }
        if (priority == null) {
            throw new IllegalArgumentException("priority must not be null");
        }
        if (priority < 0) {
            throw new IllegalArgumentException("priority must be non-negative");
        }
    }
}
