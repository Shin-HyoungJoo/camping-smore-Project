package com.green.campingsmore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class WishlistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED", length = 15)
    private Long iwish;

    @JoinColumn(name = "iuser")
    @ManyToOne
    private UserEntity userEntity;

    @JoinColumn(name = "iitem")
    @ManyToOne
    private ItemEntity itemEntity;

    @Column(name = "del_yn",columnDefinition = "TINYINT not null DEFAULT 1 CHECK(del_yn in (0,1))", length = 1)
    private Integer delYn;
}
