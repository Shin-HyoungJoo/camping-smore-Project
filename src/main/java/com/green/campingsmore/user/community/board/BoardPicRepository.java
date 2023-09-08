package com.green.campingsmore.user.community.board;

import com.green.campingsmore.entity.BoardEntity;
import com.green.campingsmore.entity.BoardImageEntity;
import com.green.campingsmore.user.camping.model.CampingDetailList;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardPicRepository extends JpaRepository<BoardImageEntity,Long> {

    @Query("SELECT b FROM BoardImageEntity b WHERE b.boardEntity.iboard = :iboard")
    List<BoardImageEntity> findByIboard(@Param("iboard") Long iboard);

}
