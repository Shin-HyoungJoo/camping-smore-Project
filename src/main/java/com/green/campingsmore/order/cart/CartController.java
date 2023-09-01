package com.green.campingsmore.order.cart;

import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.order.cart.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "장바구니")
public class CartController {

    private final CartService SERVICE;

    @GetMapping
    @Operation(
            summary = "장바구니 목록 보기",
            description = "<h3>==========================\n" +
            "<h3>icart : 카트PK\n" +
            "<h3>pic : 사진\n" +
            "<h3>name : 아이템 이름\n" +
            "<h3>price : 아이템 가격\n" +
            "<h3>quantity : 아이템 수량\n"
    )
    private ResponseEntity<Optional<List<SelCartVo>>> getCart(@AuthenticationPrincipal MyUserDetails user) {
        return ResponseEntity.ok(SERVICE.selCart(user.getIuser()));
    }

    @PostMapping
    @Operation(summary = "장바구니에 등록하기",
            description =
                    "<h3> iitem : 아이템 PK\n" +
                    "<h3> quantity : 아이템 수량\n" +
                    "<h3>==========================\n" +
                    "<h3>CODE 1 : 저장 성공\n"
    )
    private ResponseEntity<Optional<CartRes>> postCart(@AuthenticationPrincipal MyUserDetails user,
                                            @RequestBody InsCartDto dto) {
        dto.setIuser(user.getIuser());
        return ResponseEntity.ok(SERVICE.insCart(dto));
    }

    @DeleteMapping("/{icart}")
    @Operation(summary = "장바구니 목록 삭제",
            description = "<h3> icart : 삭제할 장바구니의 PK (직접 삭제시)\n" +
                    "<h3>==========================\n" +
                    "<h3>CODE 1 : 삭제성공\n"
    )
    private ResponseEntity<Long> delCart(@PathVariable Long icart) {
        return ResponseEntity.ok(SERVICE.delCart(icart));
    }

    @DeleteMapping
    @Operation(summary = "장바구니 목록 선택삭제",
            description = "<h3> icart : 체크된 장바구니의 PK (여러개),(선택 삭제시)\n" +
                    "<h3>==========================\n" +
                    "<h3>CODE number : number 갯수만큼 삭제성공\n"
    )
    private ResponseEntity<Long> delCartAll(@RequestParam List<Long> icart) {
        return ResponseEntity.ok(SERVICE.delCartAll(icart));
    }
}
