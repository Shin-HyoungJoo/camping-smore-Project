package com.green.campingsmore.user.community.board;

import com.green.campingsmore.community.board.utils.FileUtils;
import com.green.campingsmore.config.security.AuthenticationFacade;
import com.green.campingsmore.entity.*;
import com.green.campingsmore.user.community.board.model.BoardInsDto;
import com.green.campingsmore.user.community.board.model.BoardPicEntity;
import com.green.campingsmore.user.community.board.model.BoardPicRes;
import com.green.campingsmore.user.community.board.model.BoardRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.green.campingsmore.entity.QBoardEntity.*;
import static com.green.campingsmore.entity.QBoardImageEntity.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Component
public class BoardService {
    private final BoardRepository REP;
    private final AuthenticationFacade FACADE;
    private final BoardPicRepository PICREP;
    private final JPAQueryFactory queryFactory;

    @Value("${file.dir}")
    private String fileDir;

    public BoardRes postBoard() {
        UserEntity userEntity = UserEntity.builder()
                .iuser(FACADE.getLoginUserPk())
                .build();
        BoardCategoryEntity boardCategoryEntity = BoardCategoryEntity.builder()
                .icategory(1L)
                .build();
        BoardEntity boardEntity = BoardEntity.builder()
                .userEntity(userEntity)
                .title("")
                .ctnt("")
                .boardCategoryEntity(boardCategoryEntity)
                .boardView(1L)
                .delYn(1)
                .build();
        REP.save(boardEntity);
        return BoardRes.builder()
                .userEntity(boardEntity.getUserEntity())
                .boardCategoryEntity(boardEntity.getBoardCategoryEntity())
                .iboard(boardEntity.getIboard())
                .title(boardEntity.getTitle())
                .ctnt(boardEntity.getCtnt())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<String> InsPics(List<MultipartFile> pics, Long iboard) throws Exception {
        List<String> fileUrls = new ArrayList<>();
        BoardEntity boardEntity = new BoardEntity();
        if (pics != null) {
            boardEntity.setIboard(iboard);
            String centerPath = String.format("boardPics/%d", boardEntity.getIboard());
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
                    throw new Exception("파일저장을 실패하였습니다");
                }
                BoardImageEntity boardImageEntity = BoardImageEntity.builder()
                        .boardEntity(boardEntity)
                        .pic("boardPics/" + boardEntity.getIboard() + "/" + saveName)
                        .build();
                PICREP.save(boardImageEntity);

                fileUrls.add("boardPics/" + boardEntity.getIboard() + "/" + saveName);


            }

        }
        return fileUrls;
    }

    @Transactional(rollbackFor = Exception.class)
    public BoardPicRes InsOnePic(Long iboard, MultipartFile pic) throws Exception {
        BoardEntity boardEntity = new BoardEntity();
        if (pic != null) {
            String originFile = pic.getOriginalFilename();
            String saveName = FileUtils.makeRandomFileNm(originFile);
            String centerPath = String.format("boardPics/%d", boardEntity.getIboard());
            String targetPath = String.format("%s/%s", FileUtils.getAbsolutePath(fileDir), centerPath);
            File file = new File(targetPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fileTarget = new File(targetPath + "/" + saveName);
            boardEntity.setIboard(iboard);
            try {
                pic.transferTo(fileTarget);
            } catch (IOException e) {
                throw new Exception("파일저장을 실패 하였습니다");
            }
            BoardImageEntity boardImageEntity = BoardImageEntity
                    .builder().boardEntity(boardEntity)
                    .pic("boardPics/" + boardEntity.getIboard() + "/" + saveName)
                    .build();
            PICREP.save(boardImageEntity);
            return BoardPicRes.builder()
                    .pic(boardImageEntity.getPic())
                    .build();
        }
        return null;
    }

    public BoardRes updBoard(BoardInsDto dto) {
        Optional<BoardEntity> opt = REP.findById(dto.getIboard());
        if (!opt.isPresent()) {
            return null;
        }
        BoardEntity boardEntity = opt.get();
        UserEntity userEntity = UserEntity.builder()
                .iuser(FACADE.getLoginUserPk())
                .build();
        BoardCategoryEntity boardCategoryEntity = BoardCategoryEntity.builder()
                .icategory(dto.getIcategory())
                .build();
        BoardEntity entity = BoardEntity.builder()
                .iboard(boardEntity.getIboard())
                .ctnt(dto.getCtnt())
                .delYn(boardEntity.getDelYn())
                .title(dto.getTitle())
                .userEntity(userEntity)
                .boardCategoryEntity(boardCategoryEntity)
                .build();
        REP.save(entity);
        return BoardRes.builder()
                .iboard(entity.getIboard())
                .title(entity.getTitle())
                .ctnt(entity.getCtnt())
                .boardCategoryEntity(entity.getBoardCategoryEntity())
                .userEntity(entity.getUserEntity())
                .build();
    }

    @Transactional
    public void delWriteBoard(Long iboard) {
        String centerPath = String.format("boardPics/%d", iboard);
        FileUtils.delFolder(fileDir + centerPath);
        List<Long> result = queryFactory
                .select(boardImageEntity.iboardPic)
                .from(boardImageEntity)
                .where(boardImageEntity.boardEntity.iboard.eq(iboard))
                .fetch();
        log.info("1");
        for (Long list : result) {
            PICREP.deleteById(list);
        }
        log.info("2");

        REP.deleteById(iboard);
        log.info("3");
    }
}
