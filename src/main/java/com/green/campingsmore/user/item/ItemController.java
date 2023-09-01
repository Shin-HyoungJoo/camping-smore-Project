package com.green.campingsmore.user.item;

import com.green.campingsmore.user.item.model.ItemSelDetailRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@Tag(name = "아이템")
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

/*    @GetMapping("/search")
    @Operation(summary = "아이템 검색 및 검색리스트"
            , description = "" +
            "\"text\": [-] 검색어,<br>" +
            "\"page\": [-] 리스트 페이지,<br>" +
            "\"row\": [고정] 아이템 개수,<br>" +
            "\"cate\": [-] 카테고리(11: 축산물, 16: 수산물, 13: 소스/드레싱, 18: 밀키트, 17: 농산물),<br>" +
            "\"sort\": [1] 판매순 랭킹(0 : 최신순, 1: 오래된순, 2: 높은가격순, 3: 낮은가격순)  <br>")
    public ResponseEntity<ItemSelDetailRes> getSearchItem(@RequestParam(value = "cate",required=false)Long cate,
                                          @RequestParam(value = "text",required=false)String text,
                                          @PageableDefault(size = 15, sort = "createdAt",direction = Sort.Direction.DESC)Pageable pageable) {
        ItemSelDetailRes res = service.searchItem(cate,text,pageable);
        return ResponseEntity.ok(res);

    }*/

}
