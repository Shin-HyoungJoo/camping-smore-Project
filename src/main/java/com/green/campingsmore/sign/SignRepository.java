package com.green.campingsmore.sign;

import com.green.campingsmore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  SignRepository extends JpaRepository<UserEntity, Long> {
}
