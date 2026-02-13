package ir.maleki.sideprojects.nexttick.infrastructure.service;

import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotInfo;
import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotPriority;
import ir.maleki.sideprojects.nexttick.domain.service.TimeSlotPriorityQueue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class RedisTimeSlotPriorityQueue implements TimeSlotPriorityQueue {

    private static final String KEY = "timeslot:available";

    private final RedisTemplate<String, String> redisTemplate;

    public RedisTimeSlotPriorityQueue(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void rebuild(Collection<TimeSlotPriority> items) {
        redisTemplate.delete(KEY);

        for (TimeSlotPriority item : items) {
            redisTemplate.opsForZSet().add(
                    KEY,
                    item.timeSlotInfo().id().toString(),
                    item.priority()
            );
        }
    }

    @Override
    public TimeSlotInfo poll() {
        Set<String> result = redisTemplate.opsForZSet()
                .range(KEY, 0, 0);

        if (result == null || result.isEmpty()) {
            return null;
        }

        String id = result.iterator().next();

        redisTemplate.opsForZSet().remove(KEY, id);

        return new TimeSlotInfo(Long.valueOf(id));
    }

    @Override
    public void offer(TimeSlotPriority item) {
        redisTemplate.opsForZSet().add(
                KEY,
                item.timeSlotInfo().id().toString(),
                item.priority()
        );
    }
}
