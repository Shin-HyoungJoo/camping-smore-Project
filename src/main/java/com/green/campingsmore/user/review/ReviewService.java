package com.green.campingsmore.user.review;


import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.utils.MyFileUtils;
import com.green.campingsmore.entity.ReviewEntity;
import com.green.campingsmore.entity.UserEntity;
import com.green.campingsmore.order.payment.OrderRepository;
import com.green.campingsmore.sign.SignRepository;
import com.green.campingsmore.user.item.ItemRepository;
import com.green.campingsmore.user.review.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRep;
    private final AuthenticationFacade facade;
    private final SignRepository signRep;
    private final OrderRepository orderRep;
    private final ItemRepository itemRep;
    private final MyFileUtils myFileUtils;
    private final ReviewDao reviewDao;


    public ReviewVo saveReview(ReviewInsDto dto, MultipartFile pic){
        ReviewEntity entity = ReviewEntity.builder()
                .userEntity(signRep.getReferenceById(facade.getLoginUserPk()))
                .orderEntity(orderRep.getReferenceById(dto.getIorder()))
                .itemEntity(itemRep.getReferenceById(dto.getIitem()))
                .reviewCtnt(dto.getReviewCtnt())
                .starRating(dto.getStarRating())
                .delYn(1)
                .reviewLike(0)
                .build();

        reviewRep.save(entity);

        if(pic != null) {


            String target = "user/"+ entity.getUserEntity().getIuser() +"/review/" + entity.getIreview();
            String fileNm = myFileUtils.transferTo(pic, target);
            ReviewEntity picEntity = reviewRep.getReferenceById(entity.getIreview());

            picEntity.setPic(target+"/" + fileNm);

            reviewRep.save(picEntity);
        }

        return ReviewVo.builder()
                .ireview(entity.getIreview())
                .name(entity.getUserEntity().getName())
                .reviewCtnt(entity.getReviewCtnt())
                .pic(entity.getPic())
                .starRating(entity.getStarRating())
                .build();
    }

    public ReviewRes selectItemReview(Pageable page, Long iitem) {
        List<ReviewSelRes> list = reviewDao.selReview(page, iitem);
        Integer startIdx = page.getPageNumber() * page.getPageSize();
        Integer count = reviewDao.reviewCount(iitem);
        Integer maxPage = (int)Math.ceil((double) count / page.getPageSize());
        Integer isMore = maxPage > page.getPageNumber()+1 ? 1 : 0;

        return ReviewRes.builder()
                .iitem(iitem)
                .startIdx(startIdx)
                .isMore(isMore)
                .page(page.getPageNumber())
                .row(page.getPageSize())
                .list(list)
                .build();
    }


    public String updReview(ReviewUpdDto dto, MultipartFile pic) {
        UserEntity userEntity = signRep.getReferenceById(facade.getLoginUserPk());
        if(reviewDao.reviewUser(userEntity.getIuser())) {
            ReviewEntity entity = reviewRep.getReferenceById(dto.getIreview());
            entity.setReviewCtnt(dto.getReviewCtnt());
            entity.setStarRating(dto.getStarRating());


            if(pic != null) {

                String target = "user/"+ entity.getUserEntity().getIuser() +"/review/" + entity.getIreview();
                String fileNm = myFileUtils.updTransferTo(pic, target);
                entity.setPic(target+"/" + fileNm);

            }
            reviewRep.save(entity);
            return "리뷰 수정완료";
        }

        return "유저를 확인해주세요";
    }

    public Integer delReview(Long ireview) {

        ReviewEntity reviewEntity = reviewRep.getReferenceById(ireview);
        reviewEntity.setDelYn(0);
        reviewRep.save(reviewEntity);
        return 1;
    }



}
