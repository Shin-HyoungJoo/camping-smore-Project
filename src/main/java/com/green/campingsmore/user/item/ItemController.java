package com.green.campingsmore.user.item;

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
@Tag(name = "아이템")
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
            "\"text\": [-] 검색어,<br>" +
            "\"page\": [-] 리스트 페이지,<br>" +
            "\"row\": [고정] 아이템 개수,<br>" +
            "\"cate\": [-] 카테고리(11: 축산물, 16: 수산물, 13: 소스/드레싱, 18: 밀키트, 17: 농산물),<br>" +
            "\"sort\": [1] 판매순 랭킹(0 : 최신순, 1: 오래된순, 2: 높은가격순, 3: 낮은가격순)  <br>"
    )
    public ResponseEntity<ItemSelDetailRes> getSearchItem(@RequestParam(required=false)ItemSelAllParam param,
                                                          @ParameterObject @PageableDefault(sort = "iitem", direction = Sort.Direction.DESC, size = 15) Pageable page) {



        return ResponseEntity.ok(service.searchItem(page));
    }

    @GetMapping("/detail/{iitem}")
    @Operation(summary = "아이템 상세페이지"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>"+
            "\"page\": [-] 리스트 페이지,<br>" +
            "\"row\": [고정] 아이템 개수<br>" )
    public ResponseEntity<ItemDetailReviewVo> getItemDetail(@PathVariable Long iitem,
                                                            @ParameterObject @PageableDefault(sort = "ireview", direction = Sort.Direction.DESC, size = 5)Pageable page){

        return ResponseEntity.ok(service.selDetail(page, iitem));
    }
}
