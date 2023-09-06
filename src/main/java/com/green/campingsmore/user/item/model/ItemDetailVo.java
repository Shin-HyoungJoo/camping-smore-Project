package com.green.campingsmore.user.item.model;

import com.green.campingsmore.item.model.ItemSelDetailVo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ItemDetailVo {
    private ItemSelDetailVo itemSelDetailVo;
    private List<String> picList;
}
