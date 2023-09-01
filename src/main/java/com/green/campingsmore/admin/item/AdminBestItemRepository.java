package com.green.campingsmore.admin.item;

import com.green.campingsmore.entity.BestItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminBestItemRepository extends JpaRepository <BestItemEntity, Long>{
}
