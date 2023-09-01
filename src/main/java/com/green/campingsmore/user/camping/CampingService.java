package com.green.campingsmore.user.camping;

import com.green.campingsmore.community.board.utils.FileUtils;
import com.green.campingsmore.entity.CampEntity;
import com.green.campingsmore.entity.CampPicEntity;
import com.green.campingsmore.entity.NationwideEntity;
import com.green.campingsmore.user.camping.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampingService {
    private final CampingRepository REP;
    private final CityRepository CITYREP;
    private final CampingRepositoryImpl IMPL;
    private final CampingPicRepository PICREP;

    @Value("${file.dir}")
    private String fileDir;

    public CampingRes InsCamp(MultipartFile pic, CampingDto dto) throws Exception {
        CampEntity entity = new CampEntity();

        if (pic != null) {
            String originFile = pic.getOriginalFilename();
            String saveName = FileUtils.makeRandomFileNm(originFile);
            NationwideEntity nationwideEntity = NationwideEntity.builder()
                    .inationwide(dto.getInationwide())
                    .build();
            CITYREP.save(nationwideEntity);
            CampEntity campEntity = CampEntity.builder()
                    .campPhone(dto.getCampPhone())
                    .name(dto.getName())
                    .address(dto.getAddress())
                    .capacity(dto.getCapacity())
                    .note(dto.getNote())
                    .mainPic(saveName)
                    .quantity(dto.getQuantity())
                    .delyn(1)
                    .nationwideEntity(nationwideEntity)
                    .build();
            REP.save(campEntity);

            String centerPath = String.format("campPic/%d", campEntity.getIcamp());
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
            File file = new File(targetPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fileTarget = new File(targetPath + "/" + saveName);
            entity.setIcamp(campEntity.getIcamp());
            entity.setMainPic(centerPath + "/" + saveName);
            try {
                pic.transferTo(fileTarget);
            } catch (IOException e) {
                throw new Exception("파일저장을 실패했습니다");
            }
            campEntity.setMainPic("campPic/" + campEntity.getIcamp() + "/" + saveName);
            REP.save(campEntity);

            return CampingRes.builder().icamp(campEntity.getIcamp())
                    .campPhone(campEntity.getCampPhone())
                    .name(campEntity.getName())
                    .inationwide(nationwideEntity.getInationwide())
                    .address(campEntity.getAddress())
                    .capacity(campEntity.getCapacity())
                    .mainPic(campEntity.getMainPic())
                    .note(campEntity.getNote())
                    .quantity(campEntity.getQuantity())
                    .delyn(campEntity.getDelyn())
                    .build();
        }
        return null;
    }

    public CampingRes updCamping(MultipartFile pic, CampingUpdDto dto) throws Exception {
        Optional<CampEntity> opt = REP.findById(dto.getIcamp());

        if (!opt.isPresent()) {
            return null;
        }
        CampEntity entity = opt.get();
        if (pic != null) {
            String oldFilePath = entity.getMainPic();
            if (oldFilePath != null) {
                File oldFile = new File(FileUtils.getAbsolutePath(fileDir) + "/" + oldFilePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
            String originFile = pic.getOriginalFilename();
            String saveName = FileUtils.makeRandomFileNm(originFile);
            NationwideEntity nationwideEntity = NationwideEntity.builder()
                    .inationwide(dto.getInationwide())
                    .build();
            CITYREP.save(nationwideEntity);
            CampEntity campEntity = CampEntity.builder()
                    .icamp(dto.getIcamp())
                    .campPhone(dto.getCampPhone())
                    .name(dto.getName())
                    .address(dto.getAddress())
                    .capacity(dto.getCapacity())
                    .nationwideEntity(nationwideEntity)
                    .note(dto.getNote())
                    .mainPic(saveName)
                    .quantity(dto.getQuantity())
                    .delyn(1)
                    .build();
            REP.save(campEntity);

            String centerPath = String.format("campPic/%d", campEntity.getIcamp());
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
            File file = new File(targetPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fileTarget = new File(targetPath + "/" + saveName);
            entity.setIcamp(campEntity.getIcamp());
            entity.setMainPic(centerPath + "/" + saveName);
            try {
                pic.transferTo(fileTarget);
            } catch (IOException e) {
                throw new Exception("파일저장을 실패했습니다");
            }
            campEntity.setMainPic("campPic/" + campEntity.getIcamp() + "/" + saveName);
            REP.save(campEntity);

            return CampingRes.builder().icamp(campEntity.getIcamp())
                    .campPhone(campEntity.getCampPhone())
                    .name(campEntity.getName())
                    .inationwide(dto.getInationwide())
                    .address(campEntity.getAddress())
                    .capacity(campEntity.getCapacity())
                    .mainPic(campEntity.getMainPic())
                    .note(campEntity.getNote())
                    .quantity(campEntity.getQuantity())
                    .delyn(campEntity.getDelyn())
                    .build();
        }
        return null;
    }

    public CampingRes delCamping(CampingDelDto dto) {
        Optional<CampEntity> opt = REP.findById(dto.getIcamp());

        if (!opt.isPresent()) {
            return null;
        }

        CampEntity entity = opt.get();
        int newDelyn = (entity.getDelyn() == 0) ? 1 : 0;
        entity.setDelyn(newDelyn);
        REP.save(entity);

        // 기존 CampingDto를 사용하여 값을 가져오도록 수정
        CampingDto campingDto = new CampingDto(); // 여기에 값을 초기화해야 함
        campingDto.setInationwide(entity.getNationwideEntity().getInationwide());

        NationwideEntity nationwideEntity = NationwideEntity.builder()
                .inationwide(campingDto.getInationwide())
                .build();
        CITYREP.save(nationwideEntity);
        CampEntity campEntity = opt.get();

        return CampingRes.builder()
                .icamp(campEntity.getIcamp())
                .campPhone(campEntity.getCampPhone())
                .name(campEntity.getName())
                .inationwide(nationwideEntity.getInationwide()) // 기존 entity에서 가져오도록 수정
                .address(campEntity.getAddress())
                .capacity(campEntity.getCapacity())
                .mainPic(campEntity.getMainPic())
                .note(campEntity.getNote())
                .quantity(campEntity.getQuantity())
                .delyn(entity.getDelyn())
                .build();
    }


    public CampingPicRes InsPic(List<MultipartFile> pics, CampingPicDto dto) throws Exception {
        CampEntity campEntity = new CampEntity();
        if (pics != null) {
            campEntity.setIcamp(dto.getIcamp());
            String centerPath = String.format("campPics/%d", campEntity.getIcamp());
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
            File file = new File(targetPath);


            if (!file.exists()) {
                file.mkdirs();
            }
            List<CampPicEntity> picEntities = new ArrayList<>();
            for (MultipartFile pic : pics) {
                String originFile = pic.getOriginalFilename();
                String saveName = FileUtils.makeRandomFileNm(originFile);

                File fileTarget = new File(targetPath + "/" + saveName);
                try {
                    pic.transferTo(fileTarget);
                } catch (IOException e) {
                    throw new Exception("파일저장을 실패했습니다");
                }
                CampPicEntity campPicEntity = new CampPicEntity();
                campPicEntity.setCampEntity(campEntity);
                campPicEntity.setPic("campPics/" + campEntity.getIcamp() + "/" + saveName);
                picEntities.add(campPicEntity);
                CampingPicRes.builder().pic(saveName)
                                .build();
                PICREP.save(campPicEntity);
                return CampingPicRes.builder()
                        .icampPic(campEntity.getIcamp())
                        .pic(saveName)
                        .build();
            }
        }
        return null;
    }
}

























