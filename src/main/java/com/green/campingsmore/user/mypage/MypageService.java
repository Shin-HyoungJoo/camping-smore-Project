package com.green.campingsmore.user.mypage;

import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.entity.UserEntity;

import com.green.campingsmore.entity.WishlistEntity;
import com.green.campingsmore.sign.SignRepository;
import com.green.campingsmore.user.item.ItemQdsl;
import com.green.campingsmore.user.mypage.model.ReviewMypageVo;
import com.green.campingsmore.user.mypage.model.WishDto;
import com.green.campingsmore.user.mypage.model.WishMypageVo;
import com.green.campingsmore.user.review.ReviewQdsl;
import com.green.campingsmore.user.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final MypageMapper MAPPER;
    private final ReviewRepository reviewRep;
    private final ReviewQdsl reviewQdsl;
    private final SignRepository signRep;
    private final ItemQdsl itemQdsl;
    private final WishRepository wishRep;
    private final AuthenticationFacade facade;

    public Integer insertWishlist(WishDto dto){
        Integer isExist  = MAPPER.selWishitem(dto);

        if(isExist == null){//없을 경우 insert한다
            return MAPPER.insertWishlist(dto);
        } else{ // 이미 존재할 경우 del_yn = 0으로 변경시킨다.
            return MAPPER.updWishItem(dto);
        }
    }


    public List<WishMypageVo> getWishlist(){
        UserEntity userEntity = signRep.getReferenceById(facade.getLoginUserPk());
        List<WishMypageVo> vo = itemQdsl.selReviewlist(userEntity.getIuser());
    return vo;
}

    public List<ReviewMypageVo> selReviewlist() {
        UserEntity userEntity = signRep.getReferenceById(facade.getLoginUserPk());
        List<ReviewMypageVo> vo = reviewQdsl.selReviewlist(userEntity.getIuser());
        return vo;
    }

}
