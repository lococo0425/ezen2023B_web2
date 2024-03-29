package ezenweb.controller;

import ezenweb.model.dto.BoardDto;
import ezenweb.model.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired private MemberService memberService;

    @PostMapping("/signup/post.do") // 1. 회원가입
    public int doSignupPost( @RequestBody MemberDto memberDto){
        return memberService.doSignupPost( memberDto );
    }

    @PostMapping("/login/post.do") // 2. 로그인
    public boolean doLoginPost( MemberDto memberDto ){
        return memberService.doLoginPost( memberDto );
    }

    @GetMapping("/logout/get.do") // 3. 로그아웃
    public boolean doLogOutGet( ){
        return memberService.doLogOutGet();
    }

    @GetMapping("/login/info/get.do") // 4. 내정보
    public MemberDto doLoginInfo( ){
        return memberService.doLoginInfo();
    }

    @GetMapping("/find/email/get.do")
    public boolean doFindEmail(String memail){
        return memberService.getFindMeail(memail);
    }

    @GetMapping("find/myboard/get.do")
    public List<BoardDto> findByBoardList(){

        return memberService.findByMyBoardList();
    }
}