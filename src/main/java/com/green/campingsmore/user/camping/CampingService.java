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
        CampEntity campEntity = CampEntity.builder()
                .campPhone(dto.getCampPhone())
                .name(dto.getName())
                .city(dto.getCity())
                .address(dto.getAddress())
                .capacity(dto.getCapacity())
                .mainPic(dto.getMainPic())
                .note(dto.getNote())
                .quantity(dto.getQuantity())
                .build();

        REP.save(campEntity);
        return CampingRes.builder().icamp(campEntity.getIcamp())
                .campPhone(campEntity.getCampPhone())
                .name(campEntity.getName())
                .city(campEntity.getCity())
                .address(campEntity.getAddress())
                .capacity(campEntity.getCapacity())
                .mainPic(campEntity.getMainPic())
                .note(campEntity.getNote())
                .quantity(campEntity.getQuantity())
                .build();
    }
}
