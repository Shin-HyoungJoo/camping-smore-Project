package com.green.campingsmore.entity;

import com.green.campingsmore.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "best_item")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class BestItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ibest_item",updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long ibestItem;

    @JoinColumn(name = "iitem", nullable = false)
    @ManyToOne
    private ItemEntity itemEntity;

    @Column(name = "month_like", nullable = false)
    private LocalDate monthLike;
}
