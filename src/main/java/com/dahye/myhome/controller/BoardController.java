package com.dahye.myhome.controller;
import com.dahye.myhome.model.Board;
import com.dahye.myhome.model.User;
import com.dahye.myhome.repository.BoardRepository;
import com.dahye.myhome.service.BoardService;
import com.dahye.myhome.validator.BoardValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.Authenticator;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired//Dependency injection
    private BoardRepository boardRepository;

    @Autowired//Dependency injection
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list") //?page=0&size=2
    public String list(Model model, @PageableDefault(size = 5) Pageable pageable,
                       @RequestParam(required = false,defaultValue = "") String searchText){ //Model : 데이터를 전달하고 싶을 때
        //List = > Page
        //Page<Board> boards = boardRepository.findAll(pageable); //PageRequest.of(0, 20)
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        //boards.getTotalElements(); // 전체 수, 디버그 모드에서는 아래 표현식 아이콘을 클릭하면 미리 결과값을 확인가능.
        int startPage = Math.max(1,boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber()+4);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);
        return "board/list";
    }
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id==null){
            model.addAttribute("board",new Board());
        }else{
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",board);
        }
        return "board/form";
    }

    @PostMapping("/form")  //ModelAttribute
    public String postForm(@Valid Board board, BindingResult bindingResult, Authentication authentication){
        boardValidator.validate(board,bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        // 전역변수를 사용하는 방법 : Authentication a = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boardService.save(username,board);
        //boardRepository.save(board);
        return "redirect:/board/list";
    }
}
