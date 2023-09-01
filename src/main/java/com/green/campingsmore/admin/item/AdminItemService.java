package com.green.campingsmore.admin.item;

import com.green.campingsmore.admin.item.model.*;
import com.green.campingsmore.entity.BestItemEntity;
import com.green.campingsmore.entity.ItemCategoryEntity;
import com.green.campingsmore.entity.ItemDetailPicEntity;
import com.green.campingsmore.entity.ItemEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminItemService {
    private final AdminItemRepository adminItemRep;
    private final AdminItemCategoryRepository adminItemCategoryRep;
    private final AdminItemDetailPicRepository adminItemDetailPicRep;
    private final AdminBestItemEntityRepository adminBestItemEntityRep;

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
                .info(dto.getInfo())
                .stock(dto.getStock())
                .status(dto.getStatus())
                .build();
        adminItemRep.save(itemEntity);

        if(dto.getPicUrl().size() > 0) {
            for (int i = 0; i < dto.getPicUrl().size(); i++) {
                ItemDetailPicEntity itemDetailPicEntity = ItemDetailPicEntity.builder()
                        .itemEntity(adminItemRep.findById(itemEntity.getIitem()).get())
                        .pic(dto.getPicUrl().get(i).toString())
                        .build();
                adminItemDetailPicRep.save(itemDetailPicEntity);
            }
        }

        return null;
    }

    public ItemVo updItem(ItemUpdDto dto) {
        Optional<ItemEntity> optEntity = adminItemRep.findById(dto.getIitem());

        ItemEntity entity = optEntity.get();
        entity.setName(dto.getName());
        entity.setItemCategoryEntity(adminItemCategoryRep.findById(dto.getIitemCategory()).get());
        entity.setPic(dto.getPic());
        entity.setPrice(dto.getPrice());
        entity.setInfo(dto.getInfo());
        entity.setStock(dto.getStock());
        entity.setStatus(dto.getStatus());

        adminItemRep.save(entity);

        if(dto.getPicUrl().size() > 0) {

            // 수정 전에 해당 아이디에 상세이미지가 이미있으면 삭제
            List<ItemDetailPicEntity> iitemlist = adminItemDetailPicRep.findByItemEntity(optEntity.get());
            log.info("삭제할 iitem:{}",iitemlist);

            iitemlist.forEach(itemDetailPicEntity -> adminItemDetailPicRep.deleteById(itemDetailPicEntity.getIdetail()));

            //상세이미지 추가
            for (int i = 0; i < dto.getPicUrl().size(); i++) {
                ItemDetailPicEntity itemDetailPicEntity = ItemDetailPicEntity.builder()
                        .itemEntity(optEntity.get())
                        .pic(dto.getPicUrl().get(i).toString())
                        .build();
                adminItemDetailPicRep.save(itemDetailPicEntity);
            }
        }

        return null;
    }

    public ItemDetailPicVo saveItemDetailPic(ItemInsDetailPicDto dto) {
        Optional<ItemEntity> optEntity = adminItemRep.findById(dto.getIitem());


        // 수정 전에 해당 아이디에 상세이미지가 이미있으면 삭제
        List<ItemDetailPicEntity> iitemlist = adminItemDetailPicRep.findByItemEntity(optEntity.get());
        log.info("삭제할 iitem:{}",iitemlist);

        iitemlist.forEach(itemDetailPicEntity -> adminItemDetailPicRep.deleteById(itemDetailPicEntity.getIdetail()));

        //상세이미지 추가
        for (int i = 0; i < dto.getPicUrl().size(); i++) {
            ItemDetailPicEntity itemDetailPicEntity = ItemDetailPicEntity.builder()
                    .itemEntity(optEntity.get())
                    .pic(dto.getPicUrl().get(i).toString())
                    .build();
            adminItemDetailPicRep.save(itemDetailPicEntity);
        }
        return null;
    }

    public void delItem(Long iitem) {
        Optional<ItemEntity> optEntity = adminItemRep.findById(iitem);

        ItemEntity entity = optEntity.get();
        entity.setStatus(0);
        adminItemRep.save(entity);
    }

    // 추천 아이템 ------------------------------------------------------------------------------------------------------


    public ItemBestVo saveBestItem(ItemInsBestDto dto) {
        Optional<ItemEntity> optEntity = adminItemRep.findById(dto.getIitem());

        BestItemEntity entity = BestItemEntity.builder()
                .itemEntity(optEntity.get())
                .monthLike(dto.getMonthLike())
                .build();
        adminBestItemEntityRep.save(entity);
        return null;
    }

}
