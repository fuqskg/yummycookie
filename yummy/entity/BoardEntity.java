package com.cookie.yummy.entity;

import com.cookie.yummy.dto.BoardDTO;
import com.cookie.yummy.user.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//DB테이블 역할을 하는 클래스
//jpa와 함꼐 사용이 되면서 하나의 테이블 객체를 구성해주는 역할

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {

    @Id//pk컬럼 지정. 필수.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;


//    @Column // 크기 255, null가능
//    private String boardPass;

    @Column
    private String boardTitle;

    @Column
    private String boardPass;


    @Lob//대용량데이터
    private String boardContents;
    @Column(length = 20, nullable = false) //컬럼의 길이, null가능여부(크기 20, not null)
    private String boardWriter;

    @Column
    private int boardHits;

    @Column
    private int fileAttached; //1 or 0



    @ManyToOne(fetch = FetchType.LAZY) //Many: Board, One: User, Eager:무조건들고와야하는값
    @JoinColumn(name = "userId")
    private SiteUser writer; //db는 오브젝트를 저장할 수 없음. FK,자바는 오브젝트 저장가능

    //mappedby: 연관관계의 주인이 아니라는 뜻(FK가 아니니 db에 칼럼을 만들지 않음)
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CommentEntity> commentEntity;

    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    //보드 1개에 여러 파일이 올 수 있다 (리스트형태로)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();







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
        boardEntity.setFileAttached(0); //파일 없음

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

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1); //파일 있음

        return boardEntity;
    }
}
