package request;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class TestController {

    @RequestMapping(value = {"modify","delete"}, method = RequestMethod.GET)
    public String modifyString(Model model){
        model.addAttribute("message", "GET 방식으로 Modify, Delete 요청을 처리하는 핸들러 메소드 작성하기");
        return "mappingResult";
    }
}