package com.green.campingsmore.admin.item;

import com.green.campingsmore.admin.item.model.*;
import com.green.campingsmore.item.model.ItemSelDetailRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "관리자 아이템")
@RequestMapping("/api/admin/item")
@RequiredArgsConstructor
public class AdminItemController {
    private final AdminItemService service;


    // 카테고리 ------------------------------------------------------------------------------------------------------

    @PostMapping("/category")
    @Operation(summary = "아이템 카테고리 추가")
    public ResponseEntity<String> postItemCategory(@RequestBody ItemCategoryInsDto dto) {
        return ResponseEntity.ok(service.saveCategory(dto));
    }

    @GetMapping("/category")
    @Operation(summary = "아이템 카테고리 리스트"
            , description = ""+
            "\"iitemCategory\": [-] 아이템 카테고리 PK,<br>" +
            "\"name\": [-] 아이템 카테고리명,<br>" +
            "\"status\": [-] 아이템 상태(판매중(1): 유저에게 노출 / 판매중지(2): 유저에게 노출되지않음),<br>" )
    public ResponseEntity<List<AdminItemCateVo>> getCategory(){
        return ResponseEntity.ok(service.selAdminCategory());
    }

    @GetMapping("/category/{iitemcategory}/detail")
    @Operation(summary = "아이템 카테고리 디테일"
            , description = ""+
            "\"iitemCategory\": [-] 아이템 카테고리 PK,<br>" +
            "\"name\": [-] 아이템 카테고리명,<br>" +
            "\"status\": [-] 아이템 상태(판매중(1): 유저에게 노출 / 판매중지(2): 유저에게 노출되지않음),<br>" )
    public ResponseEntity<AdminItemCateDetailVo> getCategoryDetail(@PathVariable Long iitemcategory){
        return ResponseEntity.ok(service.selAdminCategoryDetail(iitemcategory));
    }

    @PutMapping("/category")
    @Operation(summary = "아이템 카테고리 업데이트"
            , description = "" +
            "\"iitemCategory\": [-] 아이템 카테고리 PK,<br>" +
            "\"name\": [-] 아이템 카테고리명,<br>" +
            "\"status\": [-] 아이템 상태(삭제(0) / 판매중(1): 유저에게 노출 / 판매중지(2): 유저에게 노출되지않음),<br>")
    public ResponseEntity<AdminItemCateVo> updCategory(@RequestBody AdminItemUpdCateDto dto) {
        AdminItemCateVo vo = service.updCategory(dto);
        return ResponseEntity.ok(vo);
    }

    @DeleteMapping("/category")
    @Operation(summary = "아이템 카테고리 삭제"
            , description = "" +
            "\"iitemCategory\": [-] 아이템 카테고리 PK<br>")
    public void delCategory(@RequestParam Long iitemCategory) {
        service.delCategory(iitemCategory);
    }

    // 아이템 ------------------------------------------------------------------------------------------------------

    @PostMapping
    @Operation(summary = "아이템 추가"
            , description = "" +
            "\"iitemCategory\": [-] 아이템 카테고리 PK,<br>" +
            "\"name\": [-] 아이템 제목,<br>" +
            "\"pic\": [-]  아이템 썸네일 pic url,<br>" +
            "\"price\": [-] 아이템 가격,<br>" +
            "\"stock\": [-] 아이템 재고,<br>" +
            "\"status\": [-] 아이템 상태(삭제(0) / 판매중(1): 유저에게 노출 / 판매중지(2): 유저에게 노출되지않음),<br>" +
            "\"picUrl\": [-] 사진 이미지 url<br>")
    public ResponseEntity<ItemVo> postItem(@RequestBody ItemInsDto dto) {
        ItemVo vo = service.saveItem(dto);
        return ResponseEntity.ok(vo);
    }

    @GetMapping("/search")
    @Operation(summary = "아이템 검색 및 검색리스트"
            , description = "" +
            "\"cate\": [-] 카테고리(11: 축산물, 16: 수산물, 13: 소스/드레싱, 18: 밀키트, 17: 농산물),<br>"+
            "\"text\": [-] 검색어,<br>" +
            "\"date\": [-] 검색 기간 선택( 0: 오늘, 3: 3일, 7: 7일, 30: 1개월, 90:3개월, 없으면 전체),<br>" +
            "\"searchStartDate\": [8] 검색 시작 날짜,<br>" +
            "\"searchEndDate\": [8] 검색 끝 날짜,<br>" +
            "\"page\": [-] 리스트 페이지,<br>" +
            "\"size\": [-] 아이템 개수,<br>" +
            "\"sort\": [1] 판매순 랭킹( iitem,DESC : 최신순(default), iitem,ASC: 오래된순, price,DESC: 높은가격순, price,ASC: 낮은가격순)  <br>"
    )
    public ResponseEntity<AdminItemSelDetailRes> getSearchItem(@RequestParam(value = "cate",required=false)Long cate,
                                                          @RequestParam(value = "text",required=false)String text,
                                                          @RequestParam(value = "date", required = false)Integer date,
                                                          @RequestParam(value = "searchStartDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate searchStartDate,
                                                          @RequestParam(value = "searchEndDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate searchEndDate,
                                                          @ParameterObject @PageableDefault(sort = "iitem", direction = Sort.Direction.DESC, size = 15) Pageable page) {



        return ResponseEntity.ok(service.searchAdminItem(page, cate, text, date, searchStartDate, searchEndDate));
    }

    @PutMapping
    @Operation(summary = "아이템 수정"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"iitemCategory\": [-] 아이템 카테고리 PK,<br>" +
            "\"name\": [-] 아이템 제목,<br>" +
            "\"pic\": [-]  아이템 썸네일 pic url,<br>" +
            "\"price\": [-] 아이템 가격,<br>" +
            "\"stock\": [-] 아이템 재고,<br>" +
            "\"status\": [-] 아이템 상태(삭제(0) / 판매중(1): 유저에게 노출 / 판매중지(2): 유저에게 노출되지않음),<br>" +
            "\"picUrl\": [-] 사진 이미지 url<br>")
    public ResponseEntity<ItemVo> putItem(@RequestBody ItemUpdDto dto) {
        ItemVo vo = service.updItem(dto);
        return ResponseEntity.ok(vo);
    }

    @PostMapping("/detail-pic")
    @Operation(summary = "아이템 상세이미지 업로드"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"picUrl\": [-] 사진 이미지 url<br>")
    public ResponseEntity<ItemDetailPicVo> postDetailPic(@RequestBody ItemInsDetailPicDto dto) {
        ItemDetailPicVo vo = service.saveItemDetailPic(dto);
        return ResponseEntity.ok(vo);
    }

    @DeleteMapping
    @Operation(summary = "아이템 삭제"
            , description = "" +
            "\"iitem\": [-] 아이템 PK<br>")
    public void delItem(@RequestParam Long iitem) {
        service.delItem(iitem);
    }

    // 추천 아이템 ------------------------------------------------------------------------------------------------------

    @PostMapping("/bestitem")
    @Operation(summary = "추천 아이템 추가"
            , description = "" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"monthLike\": [yyyy-MM-dd] 추천 아이템 노출 할 년월")
    public ResponseEntity<ItemBestVo> postBestItem(@RequestBody ItemInsBestDto dto) {
        ItemBestVo vo = service.saveBestItem(dto);
        return ResponseEntity.ok(vo);
    }

    @PutMapping("/bestitem")
    @Operation(summary = "추천 아이템 수정"
            , description = "" +
            "\"ibestItem\": [-] 베스트 아이템 PK,<br>" +
            "\"iitem\": [-] 아이템 PK,<br>" +
            "\"monthLike\": [yyyy-MM-dd] 추천 아이템 노출 할 년월")
    public ResponseEntity<ItemBestVo> updBestItem(@RequestBody AdminItemUpdBestDto dto) {
        return ResponseEntity.ok(service.updBestItem(dto));
    }

    @DeleteMapping("/bestitem")
    @Operation(summary = "추천 아이템 삭제"
            , description = "" +
            "\"ibestItem\": [-] 베스트 아이템 PK,<br>")
    public void delBestItem(@RequestParam Long ibestItem) {
        service.delBestItem(ibestItem);
    }

}
