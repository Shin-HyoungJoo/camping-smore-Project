package com.green.campingsmore.user.camping;

import com.green.campingsmore.entity.ReserveDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<ReserveDayEntity,Long> {
}
