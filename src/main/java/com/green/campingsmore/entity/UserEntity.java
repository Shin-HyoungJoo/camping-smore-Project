package com.green.campingsmore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.campingsmore.jpa.BaseEntity;
import com.green.campingsmore.security.ProviderType;
import com.green.campingsmore.security.RoleType;
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
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(name = "unique_user_user_id",columnNames = {"user_id"})})
@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class UserEntity extends BaseEntity {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 설정
    @Column(updatable = false, columnDefinition = "BIGINT UNSIGNED", length = 15)
    private Long iuser;
    @Column(name = "user_id",updatable = false, length = 20,nullable = false)
    private String uid;
    @Column(nullable = false) // length 기본값 세팅 255임
    private String password;
    @Column(length = 100,nullable = false)
    @Size(min = 10, max = 50)
    private String email;
    @Column(length = 20,nullable = false)
    private String name;
    @Column(name = "birth_date",length = 10,nullable = false)
    private String birthDate;
    @Column(length = 11,nullable = false)
    private String phone;
    @Column(columnDefinition = "TINYINT not null CHECK(gender in (0,1))",length = 1)
    private Integer gender;
    @Column(name = "user_address",length = 100,nullable = false)
    private String userAddress;
    @Column(name = "user_address_detail", length = 100)
    private String userAddressDetail;
    @Column(length = 10,nullable = false)
    @ColumnDefault("'ROLE_USER'")
    private String role;
    private String pic;
    @Column(name = "del_yn",length = 1, columnDefinition = "TINYINT")
    @ColumnDefault("1")
    @JsonIgnore
    private int delyn;
    //    @Column(name = "wishlist_array") // null true는 기본값이라서 설정안해줘도 된다.
//    private String wishlistArray;
    @Column(name = "provider_type", length = 20,nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;
    @JsonIgnore
    @Column(name = "role_type", length = 20,nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 생성자
    public UserEntity() {
        this.roleType = RoleType.USER;
        this.providerType = ProviderType.LOCAL;
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 설정
//    @Column(nullable = false, updatable = false, columnDefinition = "BIGINT UNSIGNED", length = 15)
//    private Long iuser;
//    @Column(name = "user_id",updatable = false,nullable = false, length = 20)
//    private String uid;
//    @Column(nullable = false) // length 기본값 세팅 255임
//    private String password;
//    @Column(nullable = false,length = 100)
//    @Size(min = 10, max = 50)
//    private String email;
//    @Column(nullable = false,length = 20)
//    private String name;
//    @Column(name = "birth_date",nullable = false,length = 10)
//    private String birthDate;
//    @Column(nullable = false,length = 11)
//    private String phone;
//    @Column(nullable = false,columnDefinition = "TINYINT not null CHECK(gender in (0,1))",length = 1)
//    private Integer gender;
//    @Column(name = "user_address",nullable = false,length = 100)
//    private String userAddress;
//    @Column(name = "user_address_detail",length = 100)
//    private String userAddressDetail;
//    @Column(nullable = false,length = 10)
//    @ColumnDefault("'ROLE_USER'")
//    private String role;
//    private String pic;
//    @Column(name = "del_yn",length = 1, columnDefinition = "TINYINT",nullable = false)
//    @ColumnDefault("1")
//    @JsonIgnore
//    private int delyn;
//    //    @Column(name = "wishlist_array") // null true는 기본값이라서 설정안해줘도 된다.
////    private String wishlistArray;
//    @Column(name = "provider_type", length = 20)
//    @Enumerated(EnumType.STRING)
//    private ProviderType providerType;
//    @JsonIgnore
//    @Column(name = "role_type", length = 20)
//    @Enumerated(EnumType.STRING)
//    private RoleType roleType;
    // 나머지 엔터티 코드
}