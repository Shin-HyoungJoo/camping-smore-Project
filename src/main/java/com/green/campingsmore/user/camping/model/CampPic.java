package com.green.campingsmore.user.camping.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CampPic {
    private List<String> pic;
}
