package com.green.campingsmore.user.mypage;

import com.green.campingsmore.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<WishlistEntity, Long> {
}
