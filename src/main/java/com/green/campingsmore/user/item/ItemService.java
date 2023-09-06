package com.green.campingsmore.user.item;

import com.green.campingsmore.admin.item.model.ItemVo;
import com.green.campingsmore.item.model.ItemDetailReviewVo;
import com.green.campingsmore.item.model.ItemSelDetailRes;
import com.green.campingsmore.item.model.ItemSelDetailVo;
import com.green.campingsmore.user.item.model.ItemDetailVo;
import com.green.campingsmore.user.item.model.ItemSelAllParam;
import com.green.campingsmore.user.item.model.ItemSelCateVo;
import com.green.campingsmore.user.review.ReviewService;
import com.green.campingsmore.user.review.model.ReviewRes;
import com.green.campingsmore.user.review.model.ReviewSelRes;
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
    private final ItemDao itemDao;
    private final ItemCategoryRepository itemCategoryRep;
    private final ReviewService reviewService;
    public List<ItemSelCateVo> selCategory() {
        List<ItemSelCateVo> list = itemDao.selCategory();
        return list;
    }

    public ItemSelDetailRes searchItem(Pageable page, Long cate, String text) {
        List<ItemVo> list = itemDao.searchItem(page, cate, text);
        Integer startIdx = page.getPageNumber() * page.getPageSize();
        Integer count = itemDao.itemCount();
        Integer maxPage = (int)Math.ceil((double) count / page.getPageSize());
        Integer isMore = maxPage > page.getPageNumber()+1 ? 1 : 0;

        return ItemSelDetailRes.builder()
                .iitemCategory(cate)
                .text(text)
                .startIdx(startIdx)
                .isMore(isMore)
                .page(page.getPageNumber())
                .row(page.getPageSize())
                .itemList(list)
                .build();
    }


    public ItemDetailReviewVo selDetail(Pageable page, Long iitem) {
        ItemSelDetailVo itemSelDetailVo = itemDao.selItemDetail(iitem);
        ItemDetailVo detailVo = ItemDetailVo.builder()
                .build();
        ReviewRes reviewRes = reviewService.selectItemReview(page,iitem);

/*        return ItemDetailReviewVo.builder()
                .item()
                .review(reviewRes)
                .build();*/
        return null;
    }


    // 추천 아이템 ------------------------------------------------------------------------------------------------------

    public List<ItemVo> selBestItem() {
        List<ItemVo> list = itemDao.selBestItem();
        return list;
    }
}
