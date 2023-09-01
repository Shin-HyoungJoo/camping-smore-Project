package com.green.campingsmore.user.camping;

import com.green.campingsmore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<UserEntity,Long> {
}
