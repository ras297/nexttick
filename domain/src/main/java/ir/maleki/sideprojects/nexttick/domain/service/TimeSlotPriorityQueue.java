package ir.maleki.sideprojects.nexttick.domain.service;

import java.util.Collection;

public interface TimeSlotPriorityQueue {
    void rebuild(Collection<TimeSlotPriority> items);

    TimeSlotInfo poll();

    void offer(TimeSlotPriority item);
}
