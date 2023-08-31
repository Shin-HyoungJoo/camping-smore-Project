package com.green.campingsmore.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "shipping_address")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
@SuperBuilder
public class ShippingAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, columnDefinition = "BIGINT UNSIGNED", length = 20)
    private Long iaddress;

    @JoinColumn(name = "iuser")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity iuser;

    @Column(length = 100)
    @NotNull
    private String address;

    @Column(length = 100)
    private String addressDetail;

    @Column(length = 20)
    @NotNull
    private String name;

    @Column(length = 11)
    @NotNull
    private String phone;
}