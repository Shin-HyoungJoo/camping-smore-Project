package com.green.campingsmore.admin.item;

import com.green.campingsmore.admin.item.model.ItemCategoryInsDto;
import com.green.campingsmore.admin.item.model.ItemCategoryVo;
import com.green.campingsmore.item.model.ItemInsDto;
import com.green.campingsmore.item.model.ItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "아이템 관리자")
@RequestMapping("/api/admin/item")
@RequiredArgsConstructor
public class AdminItemController {
    private final AdminItemService service;

    @PostMapping("/category")
    @Operation(summary = "아이템 카테고리 추가")
    public ResponseEntity<ItemCategoryVo> postItemCategory(@RequestBody ItemCategoryInsDto dto) {
        ItemCategoryVo vo = service.saveCategory(dto);
        return ResponseEntity.ok(vo);
    }


    @PostMapping
    public ResponseEntity<ItemVo> postItem(@RequestBody ItemInsDto dto) {
        ItemVo vo = service.saveItem(dto);
        return ResponseEntity.ok(vo);
    }
}
