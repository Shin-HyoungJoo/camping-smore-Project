package com.green.campingsmore.user.camping;

import com.green.campingsmore.community.board.utils.FileUtils;
import com.green.campingsmore.entity.*;
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
    private final ReserveRepository RESREP;

    @Value("${file.dir}")
    private String fileDir;

    public CampingRes InsCamp(MultipartFile pic, CampingDto dto) throws Exception {

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
                    .price(dto.getPrice())
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
            campEntity.setIcamp(campEntity.getIcamp());
            try {
                pic.transferTo(fileTarget);
            } catch (IOException e) {
                throw new Exception("파일저장을 실패했습니다");
            }
            campEntity.setMainPic("campPic/" + campEntity.getIcamp() + "/" + saveName);
            REP.save(campEntity);

            log.info("{}", campEntity);

            return CampingRes.builder().icamp(campEntity.getIcamp())
                    .campPhone(campEntity.getCampPhone())
                    .name(campEntity.getName())
                    .inationwide(nationwideEntity.getInationwide())
                    .address(campEntity.getAddress())
                    .capacity(campEntity.getCapacity())
                    .mainPic(campEntity.getMainPic())
                    .price(campEntity.getPrice())
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
                    .price(dto.getPrice())
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
                    .price(campEntity.getPrice())
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


    public List<String> InsPic(List<MultipartFile> pics, CampingPicDto dto) throws Exception {
        List<String> fileUrls = new ArrayList<>();
        CampEntity campEntity = new CampEntity();
        if (pics != null) {
            campEntity.setIcamp(dto.getIcamp());
            String centerPath = String.format("campPics/%d", campEntity.getIcamp());
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
            File file = new File(targetPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            for (MultipartFile pic : pics) {
                String originFile = pic.getOriginalFilename();
                String saveName = FileUtils.makeRandomFileNm(originFile);

                File fileTarget = new File(targetPath + "/" + saveName);
                try {
                    pic.transferTo(fileTarget);
                } catch (IOException e) {
                    throw new Exception("파일저장을 실패했습니다");
                }
                CampPicEntity campPicEntity = CampPicEntity.builder()
                        .campEntity(campEntity)
                        .pic("campPics/" + campEntity.getIcamp() + "/" + saveName)
                        .build();
//                campPicEntity.setCampEntity(campEntity);
//                campPicEntity.setPic();
                PICREP.save(campPicEntity);

                fileUrls.add("campPics/" + campEntity.getIcamp() + "/" + saveName);
            }

        }
        return fileUrls;
    }

    public Long delPic(CampingPicDelDto dto) {
        Optional<CampPicEntity> opt = PICREP.findById(dto.getIcampPic());
        if (!opt.isPresent()) {
            return null;
        }
        CampPicEntity entity = opt.get();

        // 파일을 삭제할 디렉토리 경로
        String centerPath = String.format("campPics/%d", dto.getIcamp());
        String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
        String result = entity.getPic();
        String picName = result.substring(result.lastIndexOf('/') + 1);
        File fileToDelete = new File(targetPath, picName);

        // 파일 삭제
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                // 파일 삭제에 성공하면 엔티티를 영구적으로 삭제
                PICREP.delete(entity);
                return entity.getIcampPic();
            } else {
                // 파일 삭제 실패 처리
                return null;
            }
        } else {
            // 파일이 존재하지 않는 경우 처리
            return null;
        }
    }
//    public ReserveRes InsReserve(ReserveDto dto){
//        Optional<CampEntity> opt = REP.findById(dto.getIcamp());
//        if (!opt.isPresent()) {
//            return null;
//        }
//        CampEntity entity = opt.get();
//        UserEntity userEntity = UserEntity.builder()
//                .iuser(dto.getIuser())
//                .build();
//        ReserveEntity reserveEntity = ReserveEntity.builder()
//                .reservation(dto.getReservation())
//                .name(dto.getName())
//                .phone(dto.getPhone())
//                .price(entity.getPrice)
//
//
//    }
}

























