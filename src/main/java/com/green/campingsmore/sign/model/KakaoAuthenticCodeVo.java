package com.green.campingsmore.sign.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoAuthenticCodeVo {
    private String authorize_code;
    private String accessToken;
}
