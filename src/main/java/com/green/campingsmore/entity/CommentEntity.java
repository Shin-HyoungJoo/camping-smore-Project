package com.green.campingsmore.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.campingsmore.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "comment")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    private Long icomment;

    @JoinColumn(name = "iboard",nullable = false)
    @ManyToOne
    private BoardEntity boardEntity;

    @JoinColumn(name = "iuser",nullable = false)
    @ManyToOne
    private UserEntity userEntity;

    @Column(nullable = false, length = 100)
    private String ctnt;

    @Column(name = "del_yn",length = 1,columnDefinition = "TINYINT not null DEFAULT 1 CHECK(del_yn in (0,1))")
    private Integer delYn;
}
