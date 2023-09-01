package com.green.campingsmore.user.item;

import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.entity.ItemCategoryEntity;
import com.green.campingsmore.entity.ItemEntity;
import com.green.campingsmore.user.item.model.ItemSelDetailRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRep;
    private final AuthenticationFacade facade;
    private final ItemCategoryRepository itemCategoryRep;


    public ItemSelDetailRes searchItem(Long cate, String text, Pageable pageable) {


/*
        ItemSelDetailRes res = ItemSelDetailRes.builder()
                .iitemCategory(itemCategoryRep.findById(cate).get().getIitemCategory())
                .text(text)
                .startIdx((pageable.getPage))
                .build();
*/

        return ItemSelDetailRes.builder().build();
    }

}
