package com.green.campingsmore.sign;

import com.green.campingsmore.entity.UserEntity;
import com.green.campingsmore.security.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  SignRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByProviderTypeAndUid(ProviderType providerType, String uid);
}
