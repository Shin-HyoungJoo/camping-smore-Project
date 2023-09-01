package com.green.campingsmore.user.review;

import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.entity.ReviewEntity;
import com.green.campingsmore.sign.SignRepository;
import com.green.campingsmore.user.review.model.ReviewInsDto;
import com.green.campingsmore.user.review.model.ReviewVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${file.dir}")
    private String fileDir;


    public ReviewVo saveReview( ReviewInsDto dto, MultipartFile pic) {


/*        ReviewEntity entity = ReviewEntity.builder()
                .userEntity(signRep.getReferenceById(facade.getLoginUserPk()))
                .orderEntity()
                .itemEntity()
                .reviewCtnt()
                .starRating()
                .build();*/


        return null;
    }
}
