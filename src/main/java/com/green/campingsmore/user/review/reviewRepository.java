package com.green.campingsmore.user.review;

import com.green.campingsmore.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface reviewRepository extends JpaRepository<ReviewEntity, Long> {
}
