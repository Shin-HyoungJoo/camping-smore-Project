package com.green.campingsmore.admin.item;

import com.green.campingsmore.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminItemRepository extends JpaRepository<ItemEntity, Long> {

}
