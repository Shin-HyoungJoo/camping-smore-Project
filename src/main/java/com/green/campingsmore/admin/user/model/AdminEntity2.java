package com.green.campingsmore.admin.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminEntity2 {
    private Long iuser;
    private String uid;
    private String password;
    private String name;
    private String role;
}
