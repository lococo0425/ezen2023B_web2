package ezenweb.controller;

import ezenweb.model.Dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@CrossOrigin("http://localhost:3001")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @PostMapping("/signup/post.do")
    public boolean doSignupPost(@RequestBody MemberDto memberDto){
        System.out.println("memberDto = " + memberDto);
        return memberService.doSignupPost(memberDto);
    }
}
