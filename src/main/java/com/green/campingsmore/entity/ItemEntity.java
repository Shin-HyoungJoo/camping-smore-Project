package com.green.campingsmore.entity;


import com.green.campingsmore.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;


@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@DynamicInsert
public class ItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,nullable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    private Long iitem;

    @JoinColumn(name = "iitemCategory")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private ItemCategoryEntity itemCategoryEntity;

    @Column(nullable = false,length = 100, name = "\"name\"")
    @NotNull
    private String name;

    @Column(nullable = false, columnDefinition = "INT UNSIGNED", length = 10)
    @NotNull
    private Integer price;

    @Column
    private String info;

    @Column(length = 500)
    private String pic;

    @Column(columnDefinition = "INT UNSIGNED",nullable = false)
    private Integer stock;

    @Column(columnDefinition = "TINYINT not null DEFAULT 1 CHECK(status in (0,1,2))", length = 1)
    private Integer status; // 삭제(0) / 노출됨, 판매중(1) / 노출되지않음, 판매중지(2)

/*    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    @ColumnDefault("1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer delYn;*/
}
