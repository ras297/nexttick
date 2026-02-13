package ir.maleki.sideprojects.nexttick.infrastructure.service;

import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotInfo;
import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotPriority;
import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotPriorityQueue;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

@Component
public class InMemoryTimeSlotPriorityQueue implements TimeSlotPriorityQueue {
    private final PriorityQueue<TimeSlotPriority> heap;
    private final Map<Long, TimeSlotPriority> index;

    public InMemoryTimeSlotPriorityQueue() {
        this.heap = new PriorityQueue<>(Comparator.comparingLong(TimeSlotPriority::priority));
        this.index = new HashMap<>();
    }

    @Override
    public synchronized void rebuild(Collection<TimeSlotPriority> items) {
        heap.clear();
        index.clear();

        for (TimeSlotPriority item : items) {
            heap.offer(item);
            index.put(item.timeSlotInfo().id(), item);
        }
    }

    @Override
    public synchronized TimeSlotInfo poll() {
        TimeSlotPriority item = heap.poll();
        if (item == null) {
            return null;
        }

        index.remove(item.timeSlotInfo().id());
        return item.timeSlotInfo();
    }

    @Override
    public synchronized void offer(TimeSlotPriority item) {
        Long id = item.timeSlotInfo().id();

        if (index.containsKey(id)) {
            return;
        }

        heap.offer(item);
        index.put(id, item);
    }
}
