package com.green.campingsmore.user.item;

import com.green.campingsmore.admin.item.model.ItemVo;
import com.green.campingsmore.item.model.ItemDetailReviewVo;
import com.green.campingsmore.item.model.ItemSelDetailRes;
import com.green.campingsmore.item.model.ItemSelDetailVo;
import com.green.campingsmore.user.item.model.ItemDetailVo;
import com.green.campingsmore.user.item.model.ItemSelCateVo;
import com.green.campingsmore.user.review.ReviewService;
import com.green.campingsmore.user.review.model.ReviewRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRep;
    private final ItemQdsl itemQdsl;
    private final ItemCategoryRepository itemCategoryRep;
    private final ReviewService reviewService;


    // 카테고리 ------------------------------------------------------------------------------------------------------

    public List<ItemSelCateVo> selCategory() {
        try {
            List<ItemSelCateVo> list = itemQdsl.selCategory();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    // 아이템 ------------------------------------------------------------------------------------------------------
    public ItemSelDetailRes searchItem(Pageable page, Long cate, String text) {
        List<ItemVo> list = itemQdsl.searchItem(page, cate, text);
        Integer startIdx = page.getPageNumber() * page.getPageSize();
        Integer count = itemQdsl.itemCount(cate, text);
        Integer maxPage = (int)Math.ceil((double) count / page.getPageSize());
        Integer isMore = maxPage > page.getPageNumber()+1 ? 1 : 0;

        return ItemSelDetailRes.builder()
                .iitemCategory(cate)
                .text(text)
                .maxPage(maxPage)
                .startIdx(startIdx)
                .isMore(isMore)
                .page(page.getPageNumber())
                .row(page.getPageSize())
                .itemList(list)
                .build();
    }


    public ItemDetailReviewVo selDetail(Pageable page, Long iitem) {
        ItemSelDetailVo itemSelDetailVo = itemQdsl.selItemDetail(iitem);
        List<String> picList = itemQdsl.selItemDetailPicList(iitem);
        ItemDetailVo detailVo = ItemDetailVo.builder()
                .itemSelDetailVo(itemSelDetailVo)
                .picList(picList)
                .build();
        ReviewRes reviewRes = reviewService.selectItemReview(page,iitem);

        return ItemDetailReviewVo.builder()
                .item(detailVo)
                .review(reviewRes)
                .build();

    }


    // 추천 아이템 ------------------------------------------------------------------------------------------------------

    public List<ItemVo> selBestItem() {
        List<ItemVo> list = itemQdsl.selBestItem();
        return list;
    }
}
