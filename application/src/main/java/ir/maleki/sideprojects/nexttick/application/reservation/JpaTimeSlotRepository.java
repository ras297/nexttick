package ir.maleki.sideprojects.nexttick.application.reservation;

import ir.maleki.sideprojects.nexttick.domain.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaTimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findAllByReservedFalseAndStartTimeAfter(LocalDateTime time);

    @Query(
            value = """
                    UPDATE AVAILABLE_SLOTS
                    SET is_reserved = true,
                        holder_id = :userId
                    WHERE id = (
                        SELECT id
                        FROM AVAILABLE_SLOTS
                        WHERE is_reserved = false
                        ORDER BY start_time
                        LIMIT 1
                        FOR UPDATE SKIP LOCKED
                    )
                    RETURNING *;
                    """,
            nativeQuery = true
    )
    TimeSlot reserveNextAvailableSlot(@Param("userId") Long userId);

}
