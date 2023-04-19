package com.cookie.yummy.repository;

import com.cookie.yummy.entity.BoardEntity;
import com.cookie.yummy.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
    // select * from comment_table where board_id=? order by id desc;

    // select * from comment_table where board_id=?까지

    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
