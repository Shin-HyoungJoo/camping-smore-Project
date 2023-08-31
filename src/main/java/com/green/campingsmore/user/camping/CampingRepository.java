package com.green.campingsmore.user.camping;

import com.green.campingsmore.entity.CampEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampingRepository extends JpaRepository<CampEntity,Long> ,CampingRepositoryCustom{
}
