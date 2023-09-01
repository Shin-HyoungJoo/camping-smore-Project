package com.green.campingsmore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "item_category")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ItemCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iitem_category", updatable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    @NotNull
    private Long iitemCategory;

    @Column(length = 50, name = "\"name\"")
    @NotNull
    private String name;
}
