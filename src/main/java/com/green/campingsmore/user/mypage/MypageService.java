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

    public int insertWishlist(WishDto dto){
        return MAPPER.insertWishlist(dto);
    }

//    public List<WishDto> getWishlist(int iuser){
//        return MAPPER.getWishlist(iuser);
//    }

//    public List<ReviewEntity2> getReviewlist(int iuser){
//        return MAPPER.getReviewlist(iuser);
//    }

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
