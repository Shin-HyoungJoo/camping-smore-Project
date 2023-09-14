package com.green.campingsmore.admin.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminUserSearch {
    private String name;
    private Integer gender;
    private String user_id;
    private String phone;
}
