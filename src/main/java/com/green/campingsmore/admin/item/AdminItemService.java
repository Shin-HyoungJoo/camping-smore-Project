package com.green.campingsmore.admin.item;

import com.green.campingsmore.admin.item.model.ItemCategoryInsDto;
import com.green.campingsmore.admin.item.model.ItemCategoryVo;
import com.green.campingsmore.entity.ItemCategoryEntity;
import com.green.campingsmore.entity.ItemEntity;
import com.green.campingsmore.admin.item.model.ItemInsDto;
import com.green.campingsmore.item.model.ItemVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminItemService {
    private final AdminItemRepository adminItemRep;
    private final AdminItemCategoryRepository adminItemCategoryRep;

    public ItemCategoryVo saveCategory(ItemCategoryInsDto dto) {
        ItemCategoryEntity entity = ItemCategoryEntity.builder()
                .name(dto.getName())
                .build();
        adminItemCategoryRep.save(entity);
        return null;
    }

    public ItemVo saveItem(ItemInsDto dto) {
        ItemCategoryEntity itemCategoryEntity = adminItemCategoryRep.findById(dto.getIitemCategory()).get();

        ItemEntity itemEntity = ItemEntity.builder()
                .itemCategoryEntity(itemCategoryEntity)
                .name(dto.getName())
                .pic(dto.getPic())
                .price(dto.getPrice())
                .build();
        adminItemRep.save(itemEntity);

        if(dto.getPicUrl().size() > 0) {

        }



        return null;
    }
}
