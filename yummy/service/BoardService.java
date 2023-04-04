package com.cookie.yummy.service;

import com.cookie.yummy.dto.BoardDTO;
import com.cookie.yummy.entity.BoardEntity;
import com.cookie.yummy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//컨트롤러로부터 호출을 넘겨받을땐 dto로 넘겨받는다
//그러고 나서 repository로 넘겨줄땐 entity로 넘겨준다
//db조회를 할때 repository로부터 entity로 받아온다
//그걸 컨트롤러로 넘겨줄때는 dto로 바꿔서 넘겨준다

//DTO -> Entity (Entity클래스에서 할것임)
//Entity -> ETO (DTO클래스애서 할것임)

@Service
@RequiredArgsConstructor
public class BoardService {

    //이 Repository는 기본적으로 Entity클래스만 받아줌
    private final BoardRepository boardRepository;

    //글쓰기
    public void save(BoardDTO boardDTO) {
        //boardRepository의 save메서드를 호출해야
        //그래야 insert를 db에 할 수 있게 됨

        //BoardEntity.toSaveEntity(boardDTO)를 작성하고 자동완성기능으로 완ㅅㅇ
        //BoardEntity의 toSaveEntity(매개변수 boardDTO)를 boardEntity에 담고
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);

        //boardEntity를 save메서드로 넘겨주게 되면 insert쿼리가 나가게 됨
        boardRepository.save(boardEntity);//매개변수는 eneity클래스타입을 받도록 돼있음(리턴도)


        //dto -> entity (entity클래스에 작성)
    }

    //글목록
    public List<BoardDTO> findAll() {
        //repository에서 뭔가를 가져올 땐 무조건 entity로 옴
        //Entity로 넘어온 List객체를 DTO 객체로 옮겨담아서 컨트롤러로 리턴을 해줘야 함
        List<BoardEntity> boardEntityList = boardRepository.findAll();

        //DTO리스트 객체 선언
        List<BoardDTO> boardDTOList = new ArrayList<>();

        //entity -> dto (dto클래스에 작성)

        //반복문으로 돌려서 출력
        //boardEntityList에 있는 걸 꺼내올거임
        for (BoardEntity boardEntity: boardEntityList) {
            //boardEntity를 DTO로 변환을 하고
            //변환된 객체를 boardDTOList에다가 모두 옮겨 담는 것
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));

        }
        //for문이 끝나면 List를 반환해준다
        return boardDTOList;
    }

    //조회쉬처리
    @Transactional // repo에 별도로 추가한 메소드인 경우에는 붙여야함
    public void updateHits(Long id) {
        boardRepository.updateHits(id);


    }

    //글조회하기
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()){
            //optionalBoardEntity.get();로 자동완성
            BoardEntity boardEntity = optionalBoardEntity.get();
            //BoardDTO.toBoardDTO(boardEntity);로 자동완성
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;

        } else {
            return null;
        }
    }

    //글 수정
    public BoardDTO update(BoardDTO boardDTO) {
        //entity로 변환
        BoardEntity boardEntity =  BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);

        //해당 개시글의 상세조회값을 넘겨준다
        //위에 있는 findById메서드가 이미 Optional작업을 했기때문에
        //그걸 호출해서 그대로 사용해준다
        return findById(boardDTO.getId());
    }

    //글삭제
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    //페이징처리
    public Page<BoardDTO> paging(Pageable pageable) {

        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한페이지에 볼 게시글 갯수

        //한페이지당 3개의 게시글을 보여주고
        //정렬은 id를 기준으로 내림차순 정렬
        //page위치에 있는 값은 0부터 시작함
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        //Entity가 담긴 Page객체가 어떤 값들을 제공해주는지 확인
        //자바 프린트문을 이용
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); //요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); //전체 글 갯수
        System.out.println("boardEntities.getContent() = " + boardEntities.getNumber()); //DB로 요청한 페이지 번호
        System.out.println("boardEntities.getContent() = " + boardEntities.getTotalPages()); //전체 페이지 갯수
        System.out.println("boardEntities.getContent() = " + boardEntities.getSize()); //한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.getContent() = " + boardEntities.hasPrevious()); //이전 페이지 존재여부
        System.out.println("boardEntities.getContent() = " + boardEntities.isFirst()); //첫 페이지 여부
        System.out.println("boardEntities.getContent() = " + boardEntities.isLast()); //마지막 페이지 여부

        // 목록: id writer title hits createdTime
        //dto로 변환완료
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));

        //컨트롤러쪽으로리턴
        return boardDTOS;

    }
}
