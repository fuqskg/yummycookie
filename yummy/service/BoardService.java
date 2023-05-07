package com.cookie.yummy.service;

import com.cookie.yummy.dto.BoardDTO;
import com.cookie.yummy.entity.BoardEntity;
import com.cookie.yummy.entity.BoardFileEntity;
import com.cookie.yummy.repository.BoardFileRepository;
import com.cookie.yummy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
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
    private final BoardFileRepository boardFileRepository;

    //글쓰기
    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            //첨부파일 없음
            /* 기존 작성했던 코드는 첨부파일 없는 경우에 해당 */

            //boardRepository의 save메서드를 호출해야
            //그래야 insert를 db에 할 수 있게 됨

            //BoardEntity.toSaveEntity(boardDTO)를 작성하고 자동완성기능으로 완ㅅㅇ
            //BoardEntity의 toSaveEntity(매개변수 boardDTO)를 boardEntity에 담고
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);

            //boardEntity를 save메서드로 넘겨주게 되면 insert쿼리가 나가게 됨
            boardRepository.save(boardEntity);//매개변수는 eneity클래스타입을 받도록 돼있음(리턴도)


        } else {
            //첨부파일 있음
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름을 가져옴
                3. 서버 저장용 이름을 만듦(사진.jpg ->2342342_사진.jpg)
                4. 저장 경로 설정
                5. 해당 경로에 파일을 저장
                6. board_table에 해당 데이터 save처리
                7. board_file_table에 해당 데이터 save 처리
             */
            //boardentity로 변환 (6번)
            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            Long saveId = boardRepository.save(boardEntity).getId();

            BoardEntity board = boardRepository.findById(saveId).get();
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {


                //MultipartFile boardFile = boardDTO.getBoardFile(); // 1번 (dto에 담긴 파일을 꺼내는 역할인데 이제 반복문을 사용하니까 필요가 없음)
                String originalFilename = boardFile.getOriginalFilename(); // 2번
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename; //3번
                String savePath = "C:/springboot_img/" + storedFileName; // 4번. (C:/springboot_img/2342342_내사진.jpg
                boardFile.transferTo(new File(savePath)); // 5번


                //boardFileEntity로 변환하는작업 (7번)
                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);

            }
        }


        //dto -> entity (entity클래스에 작성)
    }

    //글목록
    @Transactional //toBoardDTO때문에
    public List<BoardDTO> findAll() {
        //repository에서 뭔가를 가져올 땐 무조건 entity로 옴
        //Entity로 넘어온 List객체를 DTO 객체로 옮겨담아서 컨트롤러로 리턴을 해줘야 함
        List<BoardEntity> boardEntityList = boardRepository.findAll();

        //DTO리스트 객체 선언
        List<BoardDTO> boardDTOList = new ArrayList<>();

        //entity -> dto (dto클래스에 작성)

        //반복문으로 돌려서 출력
        //boardEntityList에 있는 걸 꺼내올거임
        for (BoardEntity boardEntity : boardEntityList) {
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
    @Transactional //findById에서 toBoardDTO를 호출하고 있음
    //toBoardDTO안에서 boardEntity(부모)가 boardFileEntity(자식)로 접근하고 있기 때문에
    //이런 경우엔 Transactional을 붙여줘야함
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
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
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
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

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작

        //정렬은 id를 기준으로 내림차순 정렬
        //page위치에 있는 값은 0부터 시작함
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));

        //Entity가 담긴 Page객체가 어떤 값들을 제공해주는지 확인
        //자바 프린트문을 이용
        /*
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); //요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); //전체 글 갯수
        System.out.println("boardEntities.getContent() = " + boardEntities.getNumber()); //DB로 요청한 페이지 번호
        System.out.println("boardEntities.getContent() = " + boardEntities.getTotalPages()); //전체 페이지 갯수
        System.out.println("boardEntities.getContent() = " + boardEntities.getSize()); //한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.getContent() = " + boardEntities.hasPrevious()); //이전 페이지 존재여부
        System.out.println("boardEntities.getContent() = " + boardEntities.isFirst()); //첫 페이지 여부
        System.out.println("boardEntities.getContent() = " + boardEntities.isLast()); //마지막 페이지 여부

         */

        // 목록: id writer title hits createdTime
        //dto로 변환완료
        /*
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));

        //컨트롤러쪽으로리턴
        return boardDTOS;

         */
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(),
                board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));

        return boardDTOS;
    }
}

    // 검색


