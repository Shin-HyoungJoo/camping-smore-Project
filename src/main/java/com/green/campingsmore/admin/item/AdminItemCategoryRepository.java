package com.green.campingsmore.admin.item;

import com.green.campingsmore.entity.ItemCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminItemCategoryRepository extends JpaRepository<ItemCategoryEntity, Long> {
}
