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
@Table(name = "cart")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CartEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    private Long icart;

    @JoinColumn(name = "iuser")
    @ManyToOne
    @ToString.Exclude
    private UserEntity userEntity;

    @JoinColumn(name = "iitem")
    @ManyToOne
    @ToString.Exclude
    private ItemEntity itemEntity;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 2)
    private Integer quantity;
}