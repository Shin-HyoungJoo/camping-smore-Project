package com.green.campingsmore.sign.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KaKaoLoginVo {
    private String profile_nickname;
    private String profile_image;
    private String account_email;
    private String gender;
    private String age_range;
    private String birthday;
//    private String accessToken;
//    private String refreshToken;
}
