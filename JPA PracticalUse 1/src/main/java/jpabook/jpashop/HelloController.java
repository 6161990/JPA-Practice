package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){ /* Model에 데이터를 싣어서 View에 넘긴다.*/
        model.addAttribute("data","윤지는 할 수 있다. ");
        return "hello";
    }
}
