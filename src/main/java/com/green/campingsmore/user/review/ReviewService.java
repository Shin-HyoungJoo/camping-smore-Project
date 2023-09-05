package com.green.campingsmore.user.review;


import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.utils.MyFileUtils;
import com.green.campingsmore.entity.ReviewEntity;
import com.green.campingsmore.order.payment.OrderRepository;
import com.green.campingsmore.review.model.ReviewRes;
import com.green.campingsmore.sign.SignRepository;
import com.green.campingsmore.user.item.ItemRepository;
import com.green.campingsmore.user.review.model.ReviewInsDto;
import com.green.campingsmore.user.review.model.ReviewVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
//    private final ReviewDao reviewDao;


    public ReviewVo saveReview(ReviewInsDto dto, MultipartFile pic){
        ReviewEntity entity = ReviewEntity.builder()
                .userEntity(signRep.getReferenceById(facade.getLoginUserPk()))
                .orderEntity(orderRep.getReferenceById(dto.getIorder()))
                .itemEntity(itemRep.getReferenceById(dto.getIitem()))
                .reviewCtnt(dto.getReviewCtnt())
                .starRating(dto.getStarRating())
                .delYn(0)
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

    public ReviewRes selectItemReview(Pageable pageable,Long iitem) {

/*        int count =  reviewDao.selLastReview(iitem);
        int maxPage = (int)Math.ceil((double) count /dto.getRow());*/
        ReviewRes res = ReviewRes.builder()
                .iitem(iitem)
                .startIdx((pageable.getPageNumber()-1) * pageable.getPageSize())
                .row(pageable.getPageSize())
                .build();
        return res;
    }



}
