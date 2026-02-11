package ir.maleki.sideprojects.nexttick.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    protected LocalDateTime createDate;
    @Column(nullable = false, name = "UPDATED_AT")
    protected LocalDateTime modifyDate;
    @Version
    protected Integer version;

    public BaseEntity() {
    }

    public Long id() {
        return id;
    }

    public LocalDateTime createDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createdAt) {
        this.createDate = createdAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public LocalDateTime modifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifiedAt) {
        this.modifyDate = modifiedAt;
    }

    public Integer version() {
        return version;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.modifyDate = now;
        this.createDate = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifyDate = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null || this.getClass() != that.getClass()) {
            return false;
        }
        return Objects.equals(id, ((BaseEntity) that).id);
    }

}
