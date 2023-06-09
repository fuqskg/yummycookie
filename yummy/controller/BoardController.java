package com.cookie.yummy.controller;

import com.cookie.yummy.dto.BoardDTO;
import com.cookie.yummy.dto.CommentDTO;
import com.cookie.yummy.entity.BoardEntity;
import com.cookie.yummy.service.BoardService;
import com.cookie.yummy.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.basic.BasicBorders;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/yummy") //공통주소를 써주면 이건 안써도 알아서 붙음
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;


    //글쓰기 게시판 요청(글쓰기 버튼 클릭)
    @GetMapping("/save")
    public String saveForm(){

        return "save";
    }

    //글작성 요청
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);

        return "redirect:board";
    }

    //글목록 요청
    /*
    @GetMapping("/board")
    public String findAll(Model model){
        //DB에서 전체게시글 데이터를 가져와서
        //board.html에 보여준다

        //게시글 목록 -> 1개가 아닌 여러개를 가져와야 함
        //List타입으로 (BoardDTO객체가 담겨있는)
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);

        return "board";
    }
    */

    //글 조회
    @GetMapping("/board/{id}")
    //경로상에 있는 데이터를 가져올 땐 @PathVariable을 사용
    //데이터를 담아가야 하기 때문에 모델객체를 사용
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable){
        /*
            해당 개시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */

        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);

        /* 댓글목록 가져오기 */
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "detail";
    }

    //게시글 수정폼
    @GetMapping("/board/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){

        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);

        return "update";

    }

    //게시글 수정 요청
    @PostMapping("/board/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);

        return "board";
        /*
        이렇게 해도 되지만 수정하면 조회수가 올라가는 영향이 있어서...
        return "redirect:board" + boardDTO.getId();
         */
    }

    //글 삭제
    @GetMapping("/board/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/yummy/board";
    }
    //페이징 요청 처리
    ///yummy/board?page=1
    @GetMapping("/board")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
        //pageable.getPageNumber();// 몇 페이지가 요청됐는지 값을 사용할 수 있음
        Page<BoardDTO> boardList = boardService.paging(pageable);
  //      int startPage= (((int)(Math.ceil((double)pageable.getPageNumber() / 10))) - 1) *  10; // 1 4 7 10 ~
        int startPage= ((int)Math.floor(boardList.getNumber()) / 10) * 10 + 1;
        int endPage = ((startPage + 9) < boardList.getTotalPages()) ? startPage + 9 : boardList.getTotalPages();


        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board";
    }




}
