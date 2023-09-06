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

    @Column(name = "\"name\"", nullable = false, length = 50)

    private String name;

    @Column(columnDefinition = "TINYINT not null DEFAULT 1 CHECK(status in (0,1,2))", length = 1)
    private Integer status; // 삭제(0) / 노출됨(1) / 노출되지않음(2)

}
