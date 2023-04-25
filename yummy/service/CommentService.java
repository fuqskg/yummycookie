package com.cookie.yummy.service;

import com.cookie.yummy.dto.CommentDTO;
import com.cookie.yummy.entity.BoardEntity;
import com.cookie.yummy.entity.CommentEntity;
import com.cookie.yummy.repository.BoardRepository;
import com.cookie.yummy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    //댓글저장
    public Long save(CommentDTO commentDTO) {
        /* 부모엔티티(BoardEntity) 조회 */
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }
    //전체댓글목록
    public List<CommentDTO> findAll(Long boardId) {
        // select * from comment_table where board_id=? order by id desc;
        // 최근 작성 댓글이 먼저 보이게
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        //호출결과 commentEntityList에 담기
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);

        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;


    }
}
