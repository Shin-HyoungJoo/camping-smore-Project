package com.green.campingsmore.user.mypage;


import com.green.campingsmore.user.mypage.model.WishDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    int insertWishlist(WishDto dto);
    List<WishDto> getWishlist(int iuser);
//    List<ReviewEntity2> getReviewlist(int iuser);
//    List<ReviewEntity> getOrderlist(int iuser);
}
