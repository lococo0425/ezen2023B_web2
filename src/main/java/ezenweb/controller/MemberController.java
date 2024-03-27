package ezenweb.controller;

import ezenweb.model.Dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @PostMapping("/signup/post.do")
    public boolean doSignupPost(@RequestBody MemberDto memberDto){
        System.out.println("memberDto = " + memberDto);
        return memberService.doSignupPost(memberDto);
    }

    //2. 로그인
    @PostMapping("/login/post.do")
    public boolean doLoginPost(MemberDto memberDto){
        System.out.println("memberDto = " + memberDto);
        return memberService.doLoginPost(memberDto);
    }

    //3. 로그아웃
    @GetMapping("/logout/get.do")
    public boolean doLogoutGet(MemberDto memberDto){
        return memberService.doLogoutGet(memberDto);
    }

    //4. 내정보
    @GetMapping("/login/info/get.do")
    public MemberDto doLoginInfo(){
        return memberService.doLoginInfo();
    }
}
