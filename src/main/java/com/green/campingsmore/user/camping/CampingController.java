package com.green.campingsmore.user.camping;

import com.green.campingsmore.user.camping.model.CampingDto;
import com.green.campingsmore.user.camping.model.CampingRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/camp")
@RequiredArgsConstructor
public class CampingController {
    private final CampingService SERVICE;

    @PostMapping
    public ResponseEntity<CampingRes> InsCamp(@RequestBody CampingDto dto){
        return ResponseEntity.ok(SERVICE.InsCamp(dto));
    }

}
