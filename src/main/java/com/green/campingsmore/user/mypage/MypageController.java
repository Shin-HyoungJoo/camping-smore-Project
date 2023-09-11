package com.green.campingsmore.user.mypage;

import com.green.campingsmore.config.security.AuthenticationFacade;

import com.green.campingsmore.user.mypage.model.ReviewMypageVo;
import com.green.campingsmore.user.mypage.model.WishDto;
import com.green.campingsmore.user.mypage.model.WishMypageVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "마이페이지")
@RequestMapping("/api/mypage")
public class MypageController {
    private final MypageService SERVICE;
    private final AuthenticationFacade FACADE;

    @PostMapping("/wishlist")
    @Operation(summary = "유저별로 찜하기 등록",
            description = "Try it out -> Execute 눌러주세요 \n\n "+
                    "iitem : 아이템 PK \n\n "
    )
    public int insertWishlist(@RequestParam int iitem){
        WishDto dto = new WishDto();
        dto.setIuser(Math.toIntExact(FACADE.getLoginUserPk()));
        dto.setIitem(iitem);
        return SERVICE.insertWishlist(dto);
    }

//    @GetMapping("/wishlist")
//    @Operation(summary = "유저별로 찜하기 목록 불러오기",
//            description = "Try it out -> Execute 눌러주세요 \n\n "
//    )
//    public List<WishDto> getWishlist(){
//        return SERVICE.getWishlist(Math.toIntExact(FACADE.getLoginUserPk()));
//    }

//    @GetMapping("/reviewlist")
//    @Operation(summary = "유저별로 리뷰 목록 불러오기",
//            description = "Try it out -> Execute 눌러주세요 \n\n "
//    )
//    public List<ReviewEntity2> getReviewlist(){
//        return SERVICE.getReviewlist(Math.toIntExact(FACADE.getLoginUserPk()));
//    }

    @GetMapping("/wishlist")
    @Operation(summary = "유저별로 찜하기 목록 불러오기",
            description = "Try it out -> Execute 눌러주세요 \n\n "
    )
    public ResponseEntity<List<WishMypageVo>> getWishlist(){
        return ResponseEntity.ok(SERVICE.getWishlist());
    }


    @GetMapping("/reviewlist")
    @Operation(summary = "유저별로 리뷰 목록 불러오기",
            description = "Try it out -> Execute 눌러주세요 \n\n "
    )
    public ResponseEntity<List<ReviewMypageVo>> getReviewlist(){
        return ResponseEntity.ok(SERVICE.selReviewlist());
    }


}
