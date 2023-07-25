package request;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MethodMappingTestController {

    /* 1. 메소드 방식 미지정 */
    // 요청 URL 설정
    @RequestMapping("/menu/regist") // get, post 요청을 받아서 처리함 (아직 @GetMapping,@PostMapping 정의해주지 않았기 때문에)
    public String registMenu(Model model){
        model.addAttribute("message","신규 메뉴 등록용 핸들러 메소드 호출함");

        return "mappingResult";
    }

    /* 2. 메소드 방식 지정 */
    // 요청 URL을 value 속성에 요청 method를 method 속성에 설정
    @RequestMapping(value = "/menu/modify", method = RequestMethod.GET)
    public String modify(Model md){
        md.addAttribute("message","GET 방식만 허용");

        return "mappingResult";
    }

    // 2-2 POST 요청 허용 실습
//    @RequestMapping(value = "/menu/modify", method = RequestMethod.POST)
//    public String modify(Model md){
//        md.addAttribute("message","POST 방식만 허용");
//
//        return "mappingResult";
//    }


    /* 3. 요청 메소드 전용 어노테이션
    *  Rest API : swagger
    *  요청 메소드  어노테이션
    *  POST       @PostMapping    : 등록할 때
    *  GET        @GetMapping     : 조회할 때
    *  Put        @PutMapping     : 전체에 대한 수정
    *  Delete     @DeleteMapping  : 지울 때
    *  Patch      @PatchMapping   : 일부만 사용할 때 (거의 안쓰는걸로 알고 있으니까 put, patch는 알아서 검색해보기)
    *  이 어노테이션들은 @RequestMapping 어노테이션 method 소성을 사용하여 요청 방법을 지정하는 것과 같다
    *  각 어노테이션은 해당하는 요청 메소드에 대해서만 처리할 수 있도록 제한하는 역할을 한다
    * */
    @GetMapping("/menu/delete")
    public String getDeleteMenu(Model md){
        md.addAttribute("message","GET 방식 메뉴 삭제");

        return "mappingResult";
    }

    // 3-2. POST 요청 허용 실습
    @PostMapping("/menu/delete")
    public String postDeleteMenu(Model md){
        md.addAttribute("message", "POST 방식 메뉴 삭제");
        return "mappingResult";
    }
}
