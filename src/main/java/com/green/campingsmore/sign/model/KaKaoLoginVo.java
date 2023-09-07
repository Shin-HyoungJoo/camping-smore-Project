package com.green.campingsmore.sign.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KaKaoLoginVo {
//    private String authentication_code;
    private String connected_at;
    private Long id;
    private String email;
    private String access_token;
    private String refresh_token;
}
