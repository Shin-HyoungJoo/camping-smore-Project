package com.green.campingsmore.user.camping;

import com.green.campingsmore.entity.CampEntity;
import com.green.campingsmore.user.camping.model.CampingDto;
import com.green.campingsmore.user.camping.model.CampingRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampingService {
    private final CampingRepository REP;
    private final CampingRepositoryImpl IMPL;

    public CampingRes InsCamp(CampingDto dto){
        CampEntity campEntity = REP.findById(dto.getIcamp()).get();

        REP.save(campEntity);
        return CampingRes.builder().icamp(dto.getIcamp())
                .campPhone(dto.getCampPhone())
                .name(dto.getName())
                .city(dto.getCity())
                .address(dto.getAddress())
                .capacity(dto.getCapacity())
                .mainPic(dto.getMainPic())
                .note(dto.getNote())
                .quantity(dto.getQuantity())
                .build();
    }
}
