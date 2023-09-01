package com.green.campingsmore.user.camping;

import com.green.campingsmore.entity.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<ReserveEntity,Long> {
}
