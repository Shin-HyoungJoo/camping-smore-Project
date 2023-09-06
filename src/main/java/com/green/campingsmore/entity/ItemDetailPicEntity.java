package com.green.campingsmore.entity;

import com.green.campingsmore.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "item_detail_pic")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ItemDetailPicEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    private Long idetail;

    @JoinColumn(name = "iitem", nullable = false)
    @ManyToOne
    @ToString.Exclude
    private ItemEntity itemEntity;


    @Column(nullable = false, columnDefinition="TEXT")
    private String pic;
}
