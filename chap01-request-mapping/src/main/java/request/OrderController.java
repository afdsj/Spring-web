package request;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/* 클래스 레벨에 @RequestMapping 어노테이션 사용
*  클래스 레벨에 url을 공통 부분을 이용해 설정하고 나면 매번 핸들러 메소드에 url의 중복되는 내용을 작성하지 않아도 된다
*  이 때 와일드카드를 사용하여 조금 더 포괄적인 url 패턴을 설정할 수 있다*/
@Controller
@RequestMapping("/order/*")
public class OrderController {

    // 1. Class 레벨 매핑
    @GetMapping("/regist")
    public String registOrder(Model model){
        model.addAttribute("message", "GET 방식 주문 등록 핸들러 메소드 호출");

        return "mappingResult";
    }

    @PostMapping("/regist")
    public String regitstOrder(Model model){
        model.addAttribute("message","POST 방식 주문 등록 핸들러 메소드 호출");

        return "mappingResult";
    }

    /* 실습 아래의 핸들러 메소드가 응답할 수 있도록 html을 작성해주세요*/
//    @GetMapping("/modify")
//    public String modifyOrder(Model model){
//        model.addAttribute("message", "GET 방식 주문 수정");
//
//        return "mappingResult";
//    }

    // 2.여러 개의 패턴 매핑
    // value 속성에 중괄호를 이용해서 매핑할 url을 나열한다
    @RequestMapping(value = {"modify","delete"}, method = RequestMethod.POST)
    public String modifyString (Model model){
        model.addAttribute("message", "POST 방식의 주문 정보 수정과 주문 정보 삭제를 공통으로 처리하는 핸들러 메소드 호출됨");

        return "mappingResult";
    }

    /* 실습하기
    *  GET 방식으로 Modify, Delete 요청을 처리하는 핸들러 메소드 작성하기 -> TestController ok*/


    /* 3.path variable
    *  @PathVariable 어노테이션을 이용해 요청 path로부터 변수를 받아올 수 있다
    *  PathVariable로 전달 되는 {변수명} 값은 반드시 매개변수명과 동일해야 한다
    *  만약 동일하지 않으면 @PathVariable("이름")을 설정해주어야 한다
    *  이는 Rest형 웹 서비스를 설계할 때 유용하게 사용된다 */

    @GetMapping("/detail/{orderNo}")
    public String selectOrderDetail(Model model, @PathVariable("orderNo") String orderNo){
        model.addAttribute("message",orderNo+"번 주문 상세내용 조회용 핸들러 메소드 호출함");
        return "mappingResult";
    }

    /* 4.그 외의 다른 요청 (에러가 나타나지 않고 처리)
    *  @RequestMapping 어노테이션에 아무런 URL을 설정하지 않으면 오청 처리에 대한 핸들러 메소드가 준비되지 않았을 때 해당 메소드를 호출한다 */
    @RequestMapping
    public String otherRequest(Model model){
        model.addAttribute("message", "요청은 받았지만 받지 않았습니다");
        return "mappingResult";
    }
}
