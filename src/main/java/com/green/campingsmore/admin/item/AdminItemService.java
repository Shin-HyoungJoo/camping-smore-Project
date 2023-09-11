package com.green.campingsmore.admin.item;

import com.green.campingsmore.admin.item.model.*;
import com.green.campingsmore.entity.BestItemEntity;
import com.green.campingsmore.entity.ItemCategoryEntity;
import com.green.campingsmore.entity.ItemDetailPicEntity;
import com.green.campingsmore.entity.ItemEntity;
import com.green.campingsmore.user.item.ItemQdsl;
import com.green.campingsmore.user.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final AdminBestItemRepository adminBestItemRep;
    private final ReviewService reviewService;
    private final ItemQdsl itemQdsl;


    // 카테고리 ------------------------------------------------------------------------------------------------------

    public String saveCategory(ItemCategoryInsDto dto) {
        try {
            ItemCategoryEntity entity = ItemCategoryEntity.builder()
                    .name(dto.getName())
                    .status(2)
                    .build();
            adminItemCategoryRep.save(entity);
            return "아이템 카테고리 생성 완료";
        } catch (Exception e){
            return "아이템 카테고리 생성 실패";
        }

    }

    public List<AdminItemCateVo> selAdminCategory() {
        List<AdminItemCateVo> list = itemQdsl.selAdminCategory();
        return list;
    }

    public AdminItemCateDetailVo selAdminCategoryDetail(Long iitemcategory) {
        AdminItemCateVo vo = itemQdsl.selAdminCategoryDetail(iitemcategory);
        String url = "http://주소들어갈예정/" + iitemcategory.toString();
        return AdminItemCateDetailVo.builder()
                .iitemCategory(vo.getIitemCategory())
                .name(vo.getName())
                .url(url)
                .status(vo.getStatus())
                .build();
    }

    public AdminItemCateVo updCategory(AdminItemUpdCateDto dto) {

        Optional<ItemCategoryEntity> optEntity = adminItemCategoryRep.findById(dto.getIitemCategory());

        ItemCategoryEntity entity = optEntity.get();
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());

        return AdminItemCateVo.builder()
                .iitemCategory(entity.getIitemCategory())
                .name(entity.getName())
                .status(entity.getStatus())
                .build();
    }

    public void delCategory(Long iitemCategory) {
        Optional<ItemCategoryEntity> optEntity = adminItemCategoryRep.findById(iitemCategory);

        ItemCategoryEntity entity = optEntity.get();
        entity.setStatus(0);
        adminItemCategoryRep.save(entity);

    }

    // 아이템 ------------------------------------------------------------------------------------------------------

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

    public AdminItemSelDetailRes searchAdminItem(Pageable page, Long cate, String text, Integer date, LocalDate searchStartDate, LocalDate searchEndDate) {
        List<AdminItemVo> list = itemQdsl.searchAdminItem(page, cate, text, date,searchStartDate,searchEndDate);
        Integer startIdx = page.getPageNumber() * page.getPageSize();
        Integer count = itemQdsl.itemCount();
        Integer maxPage = (int)Math.ceil((double) count / page.getPageSize());
        Integer isMore = maxPage > page.getPageNumber()+1 ? 1 : 0;

        return AdminItemSelDetailRes.builder()
                .iitemCategory(cate)
                .text(text)
                .startIdx(startIdx)
                .isMore(isMore)
                .page(page.getPageNumber())
                .row(page.getPageSize())
                .itemList(list)
                .build();
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
        adminBestItemRep.save(entity);
        return null;
    }
    public ItemBestVo updBestItem(AdminItemUpdBestDto dto) {
        Optional<BestItemEntity> optEntity = adminBestItemRep.findById(dto.getIbestItem());

        BestItemEntity entity = optEntity.get();
        entity.setItemEntity(adminItemRep.findById(dto.getIitem()).get());
        entity.setMonthLike(dto.getMonthLike());
        adminBestItemRep.save(entity);

        return ItemBestVo.builder()
                .ibestItem(entity.getIbestItem())
                .iitem(entity.getItemEntity().getIitem())
                .monthLike(entity.getMonthLike())
                .build();
    }

    public void delBestItem(Long ibestItem) {
        adminBestItemRep.deleteById(ibestItem);
    }

}
