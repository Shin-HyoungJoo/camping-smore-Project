package com.green.campingsmore.sign.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//{
//        "token_type":"bearer",
//        "access_token":"${ACCESS_TOKEN}",
//        "expires_in":43199,
//        "refresh_token":"${REFRESH_TOKEN}",
//        "refresh_token_expires_in":5184000,
//        "scope":"account_email profile"
//        }
public class KakaoToken {
    private String token_type;
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private Integer refresh_token_expires_in;
    private String scope;
}

