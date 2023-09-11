package com.green.campingsmore.user.camping;

import com.green.campingsmore.entity.CampEntity;
import com.green.campingsmore.entity.CampPicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampingPicRepository extends JpaRepository<CampPicEntity,Long> {
    List<CampEntity> findCampPicIdByCampEntity(CampEntity icamp);
}
