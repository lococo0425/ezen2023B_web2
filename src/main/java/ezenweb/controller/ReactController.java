package ezenweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReactController {
    @GetMapping(value = {"/", "/chat","/member/signup","/member/login","/board/write","/board"})
    public String reactForward(){
        return "forward:/index.html";
    }
}
