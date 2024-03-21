package ezenweb.controller;

import ezenweb.model.entity.BoardEntity;
import ezenweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController //@Controller + @ResponseBody : 해당 컨트롤러가 데이터를 주고 받는 역할
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @PostMapping("/post.do")
    public List<Object> postBoard(){

        return boardService.postBoard();
    }
    @GetMapping("/get.do")
    public boolean getBoard(){

        return boardService.getBoard();
    }
    @PutMapping("/put.do")
    public boolean putBoard(){

        return boardService.putBoard();
    }
    @DeleteMapping("/delete.do")
    public boolean deleteBoard(){

        return boardService.deleteBoard();
    }
}
