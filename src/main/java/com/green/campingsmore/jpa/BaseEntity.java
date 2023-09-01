package com.green.campingsmore.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    @ColumnDefault("current_timestamp()")
    private LocalDateTime createdAt;

    @JsonIgnore
    @LastModifiedDate
    @ColumnDefault("current_timestamp() ON UPDATE current_timestamp()")
    private LocalDateTime updatedAt;

    public String getCreatedAtDatetime() {
        return this.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public String getUpdatedAtDatetime() {
        return this.updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
