package com.green.campingsmore.user.camping;

import com.green.campingsmore.user.camping.model.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/camp")
@RequiredArgsConstructor
@Tag(name = "캠핑장")
public class CampingController {
    private final CampingService SERVICE;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CampingRes> InsCamp(@RequestPart(required = false) MultipartFile pic,
                                              @RequestPart CampingDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.InsCamp(pic, dto));
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CampingRes> updCamp(@RequestPart(required = false) MultipartFile pic,
                                              @RequestPart CampingUpdDto dto) throws Exception {
        return ResponseEntity.ok(SERVICE.updCamping(pic, dto));
    }
    @PutMapping("/delcamp")
    public ResponseEntity<CampingRes> delCamp(@RequestBody CampingDelDto dto){
        return ResponseEntity.ok(SERVICE.delCamping(dto));
    }
    @PostMapping(value = "/detail",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<String>> InsPic(@RequestPart(required = false) List<MultipartFile> pics, @RequestPart CampingPicDto dto) throws Exception{
        return ResponseEntity.ok(SERVICE.InsPic(pics,dto));
    }
}
