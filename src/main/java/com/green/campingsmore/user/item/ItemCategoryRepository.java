package com.green.campingsmore.user.item;

import com.green.campingsmore.entity.ItemCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategoryEntity, Long> {
}
