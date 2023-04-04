package com.cookie.yummy.entity;

import com.cookie.yummy.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;

//DB테이블 역할을 하는 클래스
//jpa와 함꼐 사용이 되면서 하나의 테이블 객체를 구성해주는 역할
@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {

    @Id//pk컬럼 지정. 필수.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) //컬럼의 길이, null가능여부(크기 20, not null)
    private String boardWriter;

    @Column // 크기 255, null가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    //service클래스에 entity타입을 넘겨주기 위해 하는 작업
    //dto에 담긴 값들을 entity객체로 옮겨담는 작업
    //save.html에 입력한 값을 boardDTO로 담아왔잖아
    //거기에 담겨있는 값들을 Entity의 값으로 set을 하는 것임
    //다 옮겨 닮았으면 boardEneity객체로 리턴을 줌
    public static BoardEntity toSaveEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);

        return boardEntity;


    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());

        return boardEntity;

    }
}
