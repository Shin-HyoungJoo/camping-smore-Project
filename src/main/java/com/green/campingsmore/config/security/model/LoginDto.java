package com.green.campingsmore.config.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    private Long iuser;
    private String uid;
    private String upw;
    private String name;
    private String birth_date;
    private int gender;
    private String role;
}
