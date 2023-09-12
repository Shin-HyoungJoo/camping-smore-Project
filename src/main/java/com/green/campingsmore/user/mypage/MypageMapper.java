package com.green.campingsmore.user.mypage;


import com.green.campingsmore.user.mypage.model.WishDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    Integer insertWishlist(WishDto dto);
    List<WishDto> getWishlist(int iuser);
    Integer selWishitem(WishDto dto);
    Integer updWishItem(WishDto dto);
}
