package com.green.campingsmore.admin.main;

import com.green.campingsmore.admin.main.model.SelAggregateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(name= "관리자 - 메인 (아직 건들지마시오)")
@RestController
@RequestMapping("/api/admin/main")
@RequiredArgsConstructor
public class MainController {
    private final MainService SERVICE;

    @GetMapping
    @Operation(summary = "아직 미완")
    public List<SelAggregateVO> selAggregate() {
        return SERVICE.selAggregate();
    }
}
