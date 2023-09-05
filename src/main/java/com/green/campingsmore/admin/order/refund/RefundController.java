package com.green.campingsmore.admin.order.refund;

import com.green.campingsmore.admin.order.refund.model.InsRefund;
import com.green.campingsmore.config.security.model.MyUserDetails;
import com.green.campingsmore.admin.order.refund.model.PatchRefund;
import com.green.campingsmore.admin.order.refund.model.RefundRes;
import com.green.campingsmore.admin.order.refund.model.SelRefundVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name="환불")
@RestController
@RequestMapping("/api/admin/refund")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService SERVICE;

    @PostMapping
    public RefundRes postRefund(@AuthenticationPrincipal MyUserDetails user,
                                @RequestBody InsRefund dto) {
        dto.setIuser(user.getIuser());
        return SERVICE.insRefund(dto);
    }

    @PatchMapping
    public RefundRes patchRefund(@AuthenticationPrincipal MyUserDetails user,
                                 @RequestBody PatchRefund dto) throws Exception{
        dto.setIuser(user.getIuser());
        return SERVICE.patchRefund(dto);
    }

    @DeleteMapping("/{irefund}")
    public Long delRefund(@PathVariable Long irefund) {
        return SERVICE.delRefund(irefund);
    }

    @GetMapping
    public List<SelRefundVo> selRefund(@AuthenticationPrincipal MyUserDetails user) {
        return SERVICE.selRefund(user.getIuser());
    }

}
