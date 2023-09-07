package com.green.campingsmore.user.item;

import com.green.campingsmore.admin.item.model.ItemVo;
import com.green.campingsmore.item.model.ItemDetailReviewVo;
import com.green.campingsmore.item.model.ItemSearchDto;
import com.green.campingsmore.item.model.ItemSelDetailDto;
import com.green.campingsmore.item.model.ItemSelDetailRes;
import com.green.campingsmore.user.item.model.ItemSelAllParam;
import com.green.campingsmore.user.item.model.ItemSelCateVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "유저 - 아이템")
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;
    // 카테고리 ------------------------------------------------------------------------------------------------------
    @GetMapping("/category")
    @Operation(summary = "아이템 카테고리 리스트"
            , description = "" )
    public ResponseEntity<List<ItemSelCateVo>> getCategory(){
        return ResponseEntity.ok(service.selCategory());
    }

    // 아이템 ------------------------------------------------------------------------------------------------------

    @GetMapping("/search")
    @Operation(summary = "아이템 검색 및 검색리스트"
            , description = "" +
            "\"cate\": [-] 카테고리(11: 축산물, 16: 수산물, 13: 소스/드레싱, 18: 밀키트, 17: 농산물),<br>" +
            "\"text\": [-] 검색어,<br>" +
            "\"page\": [-] 리스트 페이지,<br>" +
            "\"row\": [고정] 아이템 개수,<br>" +
            "\"sort\": [1] 판매순 랭킹( iitem,DESC : 최신순(default), iitem,ASC: 오래된순, price,DESC: 높은가격순, price,ASC: 낮은가격순)  <br>"
    )
    public ResponseEntity<ItemSelDetailRes> getSearchItem(@RequestParam(value = "cate",required=false)Long cate,
                                                          @RequestParam(value = "text",required=false)String text,
                                                          @ParameterObject @PageableDefault(sort = "iitem", direction = Sort.Direction.DESC, size = 15) Pageable page) {



        return ResponseEntity.ok(service.searchItem(page, cate, text));
    }

    @GetMapping("/detail/{iitem}")
    @Operation(summary = "아이템 상세페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>"+
            "\"page\": [-] 리스트 페이지,<br>" +
            "\"row\": [고정] 아이템 개수<br>" +
            "\"sort\": [고정] 최신순")
    public ResponseEntity<ItemDetailReviewVo> getItemDetail(@PathVariable Long iitem,
                                                            @ParameterObject @PageableDefault(sort = "ireview", direction = Sort.Direction.DESC, size = 5)Pageable page){

        return ResponseEntity.ok(service.selDetail(page, iitem));
    }

    // 추천 아이템 ------------------------------------------------------------------------------------------------------
    @GetMapping("/bestitem")
    @Operation(summary = "추천 아이템 리스트"
            , description = "" )
    public ResponseEntity<List<ItemVo>> getBestItem() {
        return ResponseEntity.ok(service.selBestItem());
    }

}
