package exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = {"/","main"}) // 해당 요청 url
    public String main(){
        return "main";
    }
}
