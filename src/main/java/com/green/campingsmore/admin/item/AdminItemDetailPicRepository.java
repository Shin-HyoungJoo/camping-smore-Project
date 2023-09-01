package com.green.campingsmore.admin.item;

import com.green.campingsmore.entity.ItemDetailPicEntity;

import com.green.campingsmore.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminItemDetailPicRepository extends JpaRepository<ItemDetailPicEntity, Long> {
    public List<ItemDetailPicEntity> findByItemEntity(ItemEntity itemEntity);
}
