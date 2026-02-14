package ir.maleki.sideprojects.nexttick.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "AVAILABLE_SLOTS")
public class TimeSlot extends BaseEntity {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(name = "IS_RESERVED")
    private Boolean reserved;

    @Column(name = "holder_id", insertable = false, updatable = false)
    private Long holderId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holder_id")
    private User holder;

    public LocalDateTime startTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime endTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean isReserved() {
        return reserved;
    }

    public User holder() {
        return holder;
    }

    public Long holderId() {
        return holderId;
    }

    public void reserveFor(User user) {
        if (this.isReserved()) {
            throw new IllegalStateException("Reserved time slot");
        }
        this.reserved = true;
        this.holder = user;
        this.holderId = holder.id();
    }
}
