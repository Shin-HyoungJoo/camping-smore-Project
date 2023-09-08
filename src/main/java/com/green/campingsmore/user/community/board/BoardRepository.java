package com.green.campingsmore.user.community.board;

import com.green.campingsmore.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
//    void deleteByIboard(Long iboard);
}
